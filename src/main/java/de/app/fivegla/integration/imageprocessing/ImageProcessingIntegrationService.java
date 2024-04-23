package de.app.fivegla.integration.imageprocessing;

import de.app.fivegla.api.dto.SortableImageOids;
import de.app.fivegla.integration.imageprocessing.model.Image;
import de.app.fivegla.integration.imageprocessing.model.ImageChannel;
import de.app.fivegla.persistence.ApplicationDataRepository;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service for  Sense integration.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageProcessingIntegrationService {

    private final ExifDataIntegrationService exifDataIntegrationService;
    private final ImageProcessingFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final ApplicationDataRepository applicationDataRepository;

    /**
     * Processes an image from the mica sense camera.
     *
     * @param transactionId    The transaction id.
     * @param imageChannel The channel the image was taken with.
     * @param base64Image      The base64 encoded tiff image.
     */
    public String processImage(Tenant tenant, String transactionId, String droneId, ImageChannel imageChannel, String base64Image) {
        var image = Base64.getDecoder().decode(base64Image);
        log.debug("Channel for the image: {}.", imageChannel);
        var micaSenseImage = applicationDataRepository.addImage(Image.builder()
                .oid(tenant.getFiwarePrefix() + droneId)
                .channel(imageChannel)
                .droneId(droneId)
                .transactionId(transactionId)
                .base64Image(base64Image)
                .location(exifDataIntegrationService.readLocation(image))
                .measuredAt(exifDataIntegrationService.readMeasuredAt(image))
                .build());
        log.debug("Image with oid {} added to the application data.", micaSenseImage.getOid());
        fiwareIntegrationServiceWrapper.createDroneDeviceMeasurement(tenant, droneId, micaSenseImage);
        return micaSenseImage.getOid();
    }

    /**
     * Returns the image with the given oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    public Optional<Image> getImage(String oid) {
        AtomicReference<Optional<Image>> result = new AtomicReference<>(Optional.empty());
        applicationDataRepository.getImage(oid).ifPresent(image -> {
            log.debug("Image with oid {} found.", oid);
            result.set(Optional.of(image));
        });
        return result.get();
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

}
