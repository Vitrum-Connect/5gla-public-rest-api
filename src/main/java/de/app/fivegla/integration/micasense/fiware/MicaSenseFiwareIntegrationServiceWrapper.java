package de.app.fivegla.integration.micasense.fiware;


import de.app.fivegla.api.Constants;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DroneDeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.InstantFormatter;
import de.app.fivegla.fiware.api.enums.DeviceCategoryValues;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.DroneDeviceMeasurement;
import de.app.fivegla.model.micasense.MicaSenseImage;
import de.app.fivegla.monitoring.FiwareEntityMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
public class MicaSenseFiwareIntegrationServiceWrapper {
    private final DeviceIntegrationService deviceIntegrationService;
    private final DroneDeviceMeasurementIntegrationService droneDeviceMeasurementIntegrationService;
    private final FiwareEntityMonitor fiwareEntityMonitor;

    @Value("${app.sensors.micasense.image.path.base.url}")
    private String imagePathBaseUrl;

    public MicaSenseFiwareIntegrationServiceWrapper(DeviceIntegrationService deviceIntegrationService,
                                                    DroneDeviceMeasurementIntegrationService droneDeviceMeasurementIntegrationService,
                                                    FiwareEntityMonitor fiwareEntityMonitor) {
        this.deviceIntegrationService = deviceIntegrationService;
        this.droneDeviceMeasurementIntegrationService = droneDeviceMeasurementIntegrationService;
        this.fiwareEntityMonitor = fiwareEntityMonitor;
    }

    /**
     * Create a new device in FIWARE.
     */
    public void createOrUpdateDevice(String droneId) {
        var fiwareId = getFiwareId(droneId);
        var device = Device.builder()
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.MicaSenseDrone.getKey()))
                        .build())
                .id(fiwareId)
                .build();
        deviceIntegrationService.persist(device);
        fiwareEntityMonitor.sensorsSavedOrUpdated(Manufacturer.MICA_SENSE);
    }

    private static String getFiwareId(String id) {
        return Constants.MICA_SENSE_DRONE_ID_PREFIX + id;
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
                .id(getFiwareId(image.getOid()))
                .channel(image.getChannel().name())
                .imagePath(imagePathBaseUrl + image.getOid())
                .build();
        droneDeviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        fiwareEntityMonitor.entitiesSavedOrUpdated(Manufacturer.MICA_SENSE);
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(MicaSenseImage image) {
        log.debug("Persisting drone image for drone: {}", image.getDroneId());
        return DeviceMeasurement.builder()
                .id(getFiwareId(getFiwareId(UUID.randomUUID().toString())))
                .refDevice(getFiwareId(image.getDroneId()))
                .dateObserved(InstantFormatter.format(image.getMeasuredAt()))
                .location(image.getLocation());
    }
}
