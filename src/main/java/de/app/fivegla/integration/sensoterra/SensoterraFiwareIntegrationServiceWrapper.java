package de.app.fivegla.integration.sensoterra;


import de.app.fivegla.api.FiwareDevicMeasurementeId;
import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.Format;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.SensoterraConfiguration;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.integration.sensoterra.model.Probe;
import de.app.fivegla.integration.sensoterra.model.ProbeData;
import de.app.fivegla.monitoring.FiwareEntityMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
public class SensoterraFiwareIntegrationServiceWrapper {
    private final DeviceIntegrationService deviceIntegrationService;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final FiwareEntityMonitor fiwareEntityMonitor;
    private final ApplicationConfiguration applicationConfiguration;

    public SensoterraFiwareIntegrationServiceWrapper(DeviceIntegrationService deviceIntegrationService,
                                                     DeviceMeasurementIntegrationService deviceMeasurementIntegrationService,
                                                     FiwareEntityMonitor fiwareEntityMonitor,
                                                     ApplicationConfiguration applicationConfiguration) {
        this.deviceIntegrationService = deviceIntegrationService;
        this.deviceMeasurementIntegrationService = deviceMeasurementIntegrationService;
        this.fiwareEntityMonitor = fiwareEntityMonitor;
        this.applicationConfiguration = applicationConfiguration;
    }

    public void persist(Probe probe, List<ProbeData> probeData) {
        try {
            persist(probe);
            probeData.forEach(probeDataEntry -> {
                log.info("Persisting measurement for probe: {}", probe);
                var deviceMeasurement = createDeviceMeasurement(probe, probeDataEntry);
                deviceMeasurementIntegrationService.persist(deviceMeasurement);
                fiwareEntityMonitor.entitiesSavedOrUpdated(Manufacturer.SENSOTERRA);
            });
        } catch (RuntimeException e) {
            log.error("Error while persisting probe data: {}", e.getMessage());
        }
    }

    private void persist(Probe probe) {
        var device = Device.builder()
                .id(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(probe.getId())))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(getManufacturerConfiguration().getKey()))
                        .build())
                .build();
        deviceIntegrationService.persist(device);
        fiwareEntityMonitor.sensorsSavedOrUpdated(Manufacturer.SENSOTERRA);
    }

    private DeviceMeasurement createDeviceMeasurement(Probe probe, ProbeData probeData) {
        log.debug("Persisting probe data for probe: {}", probe);
        log.debug("Persisting probe data: {}", probeData);
        return DeviceMeasurement.builder()
                .id(FiwareDevicMeasurementeId.create(getManufacturerConfiguration()))
                .refDevice(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(probe.getId())))
                .dateObserved(Format.format(probeData.getTimestamp()))
                .location(Location.builder()
                        .coordinates(List.of(probe.getLatitude(), probe.getLongitude()))
                        .build())
                .controlledProperty("value")
                .numValue(probeData.getValue())
                .unit(probeData.getUnit())
                .build();
    }

    private SensoterraConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().sensoterra();
    }

}
