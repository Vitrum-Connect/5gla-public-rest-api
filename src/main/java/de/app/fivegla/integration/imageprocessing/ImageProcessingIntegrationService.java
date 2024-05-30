package de.app.fivegla.integration.imageprocessing;

import de.app.fivegla.api.dto.SortableImageOids;
import de.app.fivegla.persistence.ImageRepository;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Image;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.enums.ImageChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service for  Sense integration.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageProcessingIntegrationService {

    private final ExifDataIntegrationService exifDataIntegrationService;
    private final ImageProcessingFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final ImageRepository imageRepository;

    /**
     * Processes an image from the mica sense camera.
     *
     * @param transactionId The transaction id.
     * @param group         The group.
     * @param droneId       The id of the drone.
     * @param imageChannel  The channel the image was taken with.
     * @param base64Image   The base64 encoded tiff image.
     */
    public String processImage(Tenant tenant, Group group, String transactionId, String droneId, ImageChannel imageChannel, String base64Image) {
        var image = Base64.getDecoder().decode(base64Image);
        log.debug("Channel for the image: {}.", imageChannel);
        var micaSenseImage = imageRepository.save(Image.builder()
                .oid(tenant.getFiwarePrefix() + droneId)
                .group(group.getOid())
                .channel(imageChannel)
                .droneId(droneId)
                .transactionId(transactionId)
                .base64Image(base64Image)
                .location(exifDataIntegrationService.readLocation(image))
                .measuredAt(Date.from(exifDataIntegrationService.readMeasuredAt(image)))
                .build());
        log.debug("Image with oid {} added to the application data.", micaSenseImage.getOid());
        fiwareIntegrationServiceWrapper.createDroneDeviceMeasurement(tenant, group, droneId, micaSenseImage);
        return micaSenseImage.getOid();
    }

    /**
     * Returns the image with the given oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    public Optional<Image> getImage(String oid) {
        return imageRepository.findByOid(oid);
    }

    /**
     * Retrieves the image OIDs for a given transaction.
     *
     * @param transactionId the ID of the transaction
     * @return a list of image OIDs associated with the transaction
     */
    public List<SortableImageOids> getImageOidsForTransaction(String transactionId) {
        return imageRepository.findByTransactionId(transactionId).stream()
                .map(image -> new SortableImageOids(image.getOid(), image.getMeasuredAt()))
                .sorted()
                .toList();
    }

}
