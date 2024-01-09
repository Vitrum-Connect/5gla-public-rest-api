package de.app.fivegla.integration.micasense;


import de.app.fivegla.api.FiwareDevicMeasurementeId;
import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DroneDeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.InstantFormatter;
import de.app.fivegla.fiware.model.*;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MicaSenseFiwareIntegrationServiceWrapper {
    private final DeviceIntegrationService deviceIntegrationService;
    private final DroneDeviceMeasurementIntegrationService droneDeviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    @Value("${app.sensors.micasense.imagePathBaseUrl}")
    private String imagePathBaseUrl;

    /**
     * Create a new device in FIWARE.
     */
    public void createOrUpdateDevice(String droneId) {
        var fiwareId = FiwareDeviceId.create(getManufacturerConfiguration(), droneId);
        var device = Device.builder()
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(getManufacturerConfiguration().getKey()))
                        .build())
                .location(fakeLocation())
                .manufacturerSpecificId(droneId)
                .id(fiwareId)
                .build();
        deviceIntegrationService.persist(device);
    }

    private Location fakeLocation() {
        return Location.builder()
                .coordinates(List.of(0.0, 0.0))
                .build();
    }

    /**
     * Create a new drone device measurement in FIWARE.
     *
     * @param image the image to create the measurement for
     */
    public void createDroneDeviceMeasurement(MicaSenseImage image) {
        var droneDeviceMeasurement = DroneDeviceMeasurement.builder()
                .deviceMeasurement(createDefaultDeviceMeasurement(image)
                        .build())
                .id(FiwareDevicMeasurementeId.create(getManufacturerConfiguration(), image.getOid()))
                .channel(image.getChannel().name())
                .imagePath(imagePathBaseUrl + image.getOid())
                .build();
        droneDeviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(MicaSenseImage image) {
        log.debug("Persisting drone image for drone: {}", image.getDroneId());
        return DeviceMeasurement.builder()
                .id(FiwareDevicMeasurementeId.create(getManufacturerConfiguration()))
                .refDevice(FiwareDeviceId.create(getManufacturerConfiguration(), image.getDroneId()))
                .dateObserved(InstantFormatter.format(image.getMeasuredAt()))
                .location(image.getLocation());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().micasense();
    }
}
