package de.app.fivegla.integration.imageprocessing;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.CameraImage;
import de.app.fivegla.integration.fiware.model.StationaryCameraImage;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Image;
import de.app.fivegla.persistence.entity.StationaryImage;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageProcessingFiwareIntegrationServiceWrapper {
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    @Value("${app.imagePathBaseUrl}")
    private String imagePathBaseUrl;

    /**
     * Create a new image device measurement in FIWARE.
     *
     * @param image         the image to create the measurement for
     * @param transactionId the transaction id
     */
    public void createCameraImage(Tenant tenant, Group group, String cameraId, Image image, String transactionId) {
        var deviceMeasurement = new CameraImage(
                tenant.getFiwarePrefix() + cameraId,
                EntityType.IMAGE.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute(image.getOid()),
                new TextAttribute(cameraId),
                new TextAttribute(image.getTransactionId()),
                new TextAttribute(image.getChannel().name()),
                new TextAttribute(imagePathBaseUrl + image.getFullFilename(tenant, transactionId)),
                new TextAttribute(image.getMeasuredAt().toString()),
                image.getLatitude(),
                image.getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, deviceMeasurement);
    }

    /**
     * Create a new stationary image device measurement in FIWARE.
     *
     * @param micaSenseImage the image to create the measurement for
     */
    public void createStationaryCameraImage(Tenant tenant, Group group, String cameraId, StationaryImage micaSenseImage) {
        var deviceMeasurement = new StationaryCameraImage(
                tenant.getFiwarePrefix() + cameraId,
                EntityType.STATIONARY_IMAGE.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute(micaSenseImage.getOid()),
                new TextAttribute(cameraId),
                new TextAttribute(micaSenseImage.getChannel().name()),
                new TextAttribute(imagePathBaseUrl + micaSenseImage.getFullFilename(tenant)),
                new TextAttribute(micaSenseImage.getMeasuredAt().toString()),
                micaSenseImage.getLatitude(),
                micaSenseImage.getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, deviceMeasurement);
    }
}
