package de.app.fivegla.integration.micasense;


import de.app.fivegla.api.FiwareIdGenerator;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DroneDeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.InstantFormatter;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.DroneDeviceMeasurement;
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
    private final DroneDeviceMeasurementIntegrationService droneDeviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    @Value("${app.sensors.micasense.imagePathBaseUrl}")
    private String imagePathBaseUrl;

    /**
     * Create a new drone device measurement in FIWARE.
     *
     * @param image the image to create the measurement for
     */
    public void createDroneDeviceMeasurement(String droneId, MicaSenseImage image) {
        var droneDeviceMeasurement = DroneDeviceMeasurement.builder()
                .deviceMeasurement(createDefaultDeviceMeasurement(droneId, image)
                        .build())
                .id(FiwareIdGenerator.create(getManufacturerConfiguration(), image.getOid()))
                .channel(image.getChannel().name())
                .imagePath(imagePathBaseUrl + image.getOid())
                .build();
        droneDeviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(String droneId, MicaSenseImage image) {
        log.debug("Persisting drone image for drone: {}", image.getDroneId());
        return DeviceMeasurement.builder()
                .id(FiwareIdGenerator.create(getManufacturerConfiguration(), droneId))
                .manufacturerSpecificId(droneId)
                .dateObserved(InstantFormatter.format(image.getMeasuredAt()))
                .location(image.getLocation());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().micasense();
    }
}
