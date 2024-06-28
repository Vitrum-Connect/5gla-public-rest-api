package de.app.fivegla.integration.imageprocessing;

import de.app.fivegla.api.dto.SortableImageOids;
import de.app.fivegla.integration.imageprocessing.dto.TransactionIdWithTheFirstImageTimestamp;
import de.app.fivegla.persistence.ImageRepository;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Image;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.enums.ImageChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * Service for  Sense integration.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageProcessingIntegrationService {

    private final ExifDataIntegrationService exifDataIntegrationService;
    private final ImageProcessingFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final PersistentStorageIntegrationService persistentStorageIntegrationService;
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
        var decodedImage = Base64.getDecoder().decode(base64Image);
        log.debug("Channel for the decodedImage: {}.", imageChannel);
        var point = exifDataIntegrationService.readLocation(decodedImage);
        var image = new Image();
        image.setOid(UUID.randomUUID().toString());
        image.setGroup(group);
        image.setTenant(tenant);
        image.setDroneId(droneId);
        image.setTransactionId(transactionId);
        image.setChannel(imageChannel);
        image.setLongitude(point.getX());
        image.setLatitude(point.getY());
        image.setMeasuredAt((Date.from(exifDataIntegrationService.readMeasuredAt(decodedImage))));
        image.setBase64encodedImage(base64Image);
        var micaSenseImage = imageRepository.save(image);
        log.debug("Image with oid {} added to the application data.", micaSenseImage.getOid());
        fiwareIntegrationServiceWrapper.createDroneDeviceMeasurement(tenant, group, droneId, micaSenseImage);
        persistentStorageIntegrationService.storeImage(transactionId, micaSenseImage);
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

    /**
     * Retrieves all images for a given transaction.
     *
     * @param transactionId the ID of the transaction
     * @param channel       the channel of the image
     * @param tenantId      the ID of the tenant
     * @return a list of images associated with the transaction
     */
    public List<Image> getAllImagesForTransaction(String transactionId, ImageChannel channel, String tenantId) {
        return imageRepository.findByTransactionIdAndChannelAndTenantTenantId(transactionId, channel, tenantId);
    }

    /**
     * Retrieves a list of TransactionIdWithTheFirstImageTimestamp objects for a given tenant
     * within a specified time frame.
     *
     * @param from     the starting time instant
     * @param to       the ending time instant
     * @param tenantId the unique identifier for the tenant
     * @return a list of TransactionIdWithTheFirstImageTimestamp objects
     */
    public List<TransactionIdWithTheFirstImageTimestamp> listAllTransactionsForTenant(Instant from, Instant to, String tenantId) {
        var allTransactionsForTenant = new ArrayList<TransactionIdWithTheFirstImageTimestamp>();
        var allTransactionIdsWithinTimeFrame = imageRepository.findAllTransactionIdsWithinTimeFrame(tenantId, from, to);
        for (String transactionId : allTransactionIdsWithinTimeFrame) {
            var image = imageRepository.findFirstByTransactionIdOrderByMeasuredAtAsc(transactionId);
            var transactionIdWithTheFirstImageTimestamp = new TransactionIdWithTheFirstImageTimestamp(transactionId, image.getMeasuredAt().toInstant());
            allTransactionsForTenant.add(transactionIdWithTheFirstImageTimestamp);
        }
        return allTransactionsForTenant;
    }
}
