package de.app.fivegla.integration.micasense;

import de.app.fivegla.api.FiwareIdGenerator;
import de.app.fivegla.api.dto.SortableImageOids;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.integration.micasense.events.ImageProcessingFinishedEvent;
import de.app.fivegla.integration.micasense.events.ImageProcessingStartedEvent;
import de.app.fivegla.integration.micasense.model.MicaSenseChannel;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
import de.app.fivegla.integration.micasense.transactions.ActiveMicaSenseTransactions;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service for Mica Sense integration.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MicaSenseIntegrationService {

    private final ExifDataIntegrationService exifDataIntegrationService;
    private final MicaSenseFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final ApplicationDataRepository applicationDataRepository;
    private final ActiveMicaSenseTransactions activeMicaSenseTransactions;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationConfiguration applicationConfiguration;

    /**
     * Processes an image from the mica sense camera.
     *
     * @param transactionId    The transaction id.
     * @param micaSenseChannel The channel the image was taken with.
     * @param base64Image      The base64 encoded tiff image.
     */
    public String processImage(String transactionId, String droneId, MicaSenseChannel micaSenseChannel, String base64Image) {
        var image = Base64.getDecoder().decode(base64Image);
        var location = exifDataIntegrationService.readLocation(image);
        log.debug("Channel for the image: {}.", micaSenseChannel);
        log.debug("Location for the image: {}.", location.getCoordinates());
        var micaSenseImage = applicationDataRepository.addMicaSenseImage(MicaSenseImage.builder()
                .oid(FiwareIdGenerator.create(getManufacturerConfiguration(), droneId))
                .channel(micaSenseChannel)
                .droneId(droneId)
                .transactionId(transactionId)
                .base64Image(base64Image)
                .location(exifDataIntegrationService.readLocation(image))
                .measuredAt(exifDataIntegrationService.readMeasuredAt(image))
                .build());
        log.debug("Image with oid {} added to the application data.", micaSenseImage.getOid());
        fiwareIntegrationServiceWrapper.createDroneDeviceMeasurement(droneId, micaSenseImage);
        activeMicaSenseTransactions.add(transactionId, micaSenseImage.getOid());
        return micaSenseImage.getOid();
    }

    /**
     * Returns the image with the given oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    public Optional<MicaSenseImage> getImage(String oid) {
        AtomicReference<Optional<MicaSenseImage>> result = new AtomicReference<>(Optional.empty());
        applicationDataRepository.getImage(oid).ifPresent(image -> {
            log.debug("Image with oid {} found.", oid);
            result.set(Optional.of(image));
        });
        return result.get();
    }

    /**
     * Ends the image processing for a transaction.
     *
     * @param droneId       The ID of the drone.
     * @param transactionId The ID of the transaction.
     */
    public void endImageProcessing(String droneId, String transactionId) {
        applicationEventPublisher.publishEvent(new ImageProcessingFinishedEvent(this, droneId, transactionId));
    }

    /**
     * Retrieves the image OIDs for a given transaction.
     *
     * @param transactionId the ID of the transaction
     * @return a list of image OIDs associated with the transaction
     */
    public List<SortableImageOids> getImageOidsForTransaction(String transactionId) {
        return applicationDataRepository.getImageOidsForTransaction(transactionId).stream().sorted().toList();
    }

    /**
     * Begins image processing for a specific drone and transaction.
     *
     * @param droneId       The ID of the drone for which the image processing is starting.
     * @param transactionId The ID of the transaction associated with the image processing.
     */
    public void beginImageProcessing(String droneId, String transactionId) {
        applicationEventPublisher.publishEvent(new ImageProcessingStartedEvent(this, droneId, transactionId));
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().micasense();
    }

}
