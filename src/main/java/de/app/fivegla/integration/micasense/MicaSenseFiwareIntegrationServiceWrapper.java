package de.app.fivegla.integration.micasense;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
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
public class MicaSenseFiwareIntegrationServiceWrapper {
    private final ApplicationConfiguration applicationConfiguration;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    @Value("${app.sensors.micasense.imagePathBaseUrl}")
    private String imagePathBaseUrl;

    /**
     * Create a new drone device measurement in FIWARE.
     *
     * @param image the image to create the measurement for
     */
    public void createDroneDeviceMeasurement(String droneId, MicaSenseImage image) {
        var deviceMeasurement = new DeviceMeasurement(
                getManufacturerConfiguration().fiwarePrefix() + droneId,
                MeasurementType.MICASENSE_IMAGE.name(),
                new TextAttribute("image"),
                new EmptyAttribute(),
                new DateTimeAttribute(image.getMeasuredAt()),
                new TextAttribute(imagePathBaseUrl + image.getOid()),
                image.getLocation().getX(),
                image.getLocation().getY());
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().micasense();
    }
}
