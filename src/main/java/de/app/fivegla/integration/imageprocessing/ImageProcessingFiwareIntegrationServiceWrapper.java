package de.app.fivegla.integration.imageprocessing;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.imageprocessing.model.Image;
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
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    @Value("${app.imagePathBaseUrl}")
    private String imagePathBaseUrl;

    /**
     * Create a new drone device measurement in FIWARE.
     *
     * @param image the image to create the measurement for
     */
    public void createDroneDeviceMeasurement(Tenant tenant, String droneId, Image image) {
        var deviceMeasurement = new DeviceMeasurement(
                tenant.getFiwarePrefix() + droneId,
                MeasurementType.MICASENSE_IMAGE.getKey(),
                new TextAttribute("image"),
                new EmptyAttribute(),
                new DateTimeAttribute(image.getMeasuredAt()),
                new TextAttribute(imagePathBaseUrl + image.getOid()),
                image.getLocation().getX(),
                image.getLocation().getY());
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
    }

}
