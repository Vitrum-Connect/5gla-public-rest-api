package de.app.fivegla.integration.micasense;


import de.app.fivegla.api.FiwareDevicMeasurementeId;
import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DroneDeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.InstantFormatter;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.DroneDeviceMeasurement;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
import de.app.fivegla.monitoring.FiwareEntityMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
        var fiwareId = FiwareDeviceId.create(Manufacturer.MICA_SENSE, droneId);
        var device = Device.builder()
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(Manufacturer.MICA_SENSE.key()))
                        .build())
                .id(fiwareId)
                .build();
        deviceIntegrationService.persist(device);
        fiwareEntityMonitor.sensorsSavedOrUpdated(Manufacturer.MICA_SENSE);
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
                .id(FiwareDevicMeasurementeId.create(Manufacturer.MICA_SENSE, image.getOid()))
                .channel(image.getChannel().name())
                .imagePath(imagePathBaseUrl + image.getOid())
                .build();
        droneDeviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        fiwareEntityMonitor.entitiesSavedOrUpdated(Manufacturer.MICA_SENSE);
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(MicaSenseImage image) {
        log.debug("Persisting drone image for drone: {}", image.getDroneId());
        return DeviceMeasurement.builder()
                .id(FiwareDevicMeasurementeId.create(Manufacturer.MICA_SENSE))
                .refDevice(FiwareDeviceId.create(Manufacturer.MICA_SENSE, image.getDroneId()))
                .dateObserved(InstantFormatter.format(image.getMeasuredAt()))
                .location(image.getLocation());
    }
}
