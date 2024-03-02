package de.app.fivegla.integration.micasense;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
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
        var deviceMeasurement = createDefaultDeviceMeasurement(droneId, image)
                .withMeasurement("image",
                        FiwareType.TEXT,
                        0.0,
                        image.getMeasuredAt(),
                        image.getLocation().getX(),
                        image.getLocation().getY())
                .withExternalDataReference(imagePathBaseUrl + image.getOid())
                .build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
    }

    private DeviceMeasurementBuilder createDefaultDeviceMeasurement(String droneId, MicaSenseImage image) {
        log.debug("Persisting drone image for drone: {}", image.getDroneId());
        return new DeviceMeasurementBuilder()
                .withId(getManufacturerConfiguration().fiwarePrefix() + droneId)
                .withType(MeasurementType.MICASENSE_IMAGE.name());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().micasense();
    }
}
