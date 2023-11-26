package de.app.fivegla.integration.micasense;

import de.app.fivegla.fiware.api.FiwareIdGenerator;
import de.app.fivegla.integration.micasense.events.ImageProcessingFinishedEvent;
import de.app.fivegla.integration.micasense.model.MicaSenseChannel;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
import de.app.fivegla.integration.micasense.transactions.ActiveMicaSenseTransactions;
import de.app.fivegla.persistence.ApplicationDataRepository;
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
public class MicaSenseIntegrationService {

    private final ExifDataIntegrationService exifDataIntegrationService;
    private final MicaSenseFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final ApplicationDataRepository applicationDataRepository;
    private final ActiveMicaSenseTransactions activeMicaSenseTransactions;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MicaSenseIntegrationService(ExifDataIntegrationService exifDataIntegrationService,
                                       MicaSenseFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper,
                                       ApplicationDataRepository applicationDataRepository,
                                       ActiveMicaSenseTransactions activeMicaSenseTransactions,
                                       ApplicationEventPublisher applicationEventPublisher) {
        this.exifDataIntegrationService = exifDataIntegrationService;
        this.fiwareIntegrationServiceWrapper = fiwareIntegrationServiceWrapper;
        this.applicationDataRepository = applicationDataRepository;
        this.activeMicaSenseTransactions = activeMicaSenseTransactions;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Processes an image from the mica sense camera.
     *
     * @param transactionId    The transaction id.
     * @param micaSenseChannel The channel the image was taken with.
     * @param base64Image      The base64 encoded tiff image.
     */
    public String processImage(String transactionId, String droneId, MicaSenseChannel micaSenseChannel, String base64Image) {
        fiwareIntegrationServiceWrapper.createOrUpdateDevice(droneId);
        var image = Base64.getDecoder().decode(base64Image);
        var location = exifDataIntegrationService.readLocation(image);
        log.debug("Channel for the image: {}.", micaSenseChannel);
        log.debug("Location for the image: {}.", location.getCoordinates());
        var micaSenseImage = applicationDataRepository.addMicaSenseImage(MicaSenseImage.builder()
                .oid(FiwareIdGenerator.id())
                .channel(micaSenseChannel)
                .droneId(droneId)
                .transactionId(transactionId)
                .base64Image(base64Image)
                .location(exifDataIntegrationService.readLocation(image))
                .measuredAt(exifDataIntegrationService.readMeasuredAt(image))
                .build());
        log.debug("Image with oid {} added to the application data.", micaSenseImage.getOid());
        fiwareIntegrationServiceWrapper.createDroneDeviceMeasurement(micaSenseImage);
        activeMicaSenseTransactions.add(transactionId, droneId, micaSenseImage.getOid());
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

    public List<String> getImageOidsForTransaction(String transactionId) {
        return applicationDataRepository.getImageOidsForTransaction(transactionId);
    }
}
