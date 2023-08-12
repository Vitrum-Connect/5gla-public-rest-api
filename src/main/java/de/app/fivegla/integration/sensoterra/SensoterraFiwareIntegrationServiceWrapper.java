package de.app.fivegla.integration.sensoterra;


import de.app.fivegla.api.FiwareDevicMeasurementeId;
import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.InstantFormat;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
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

    public SensoterraFiwareIntegrationServiceWrapper(DeviceIntegrationService deviceIntegrationService,
                                                     DeviceMeasurementIntegrationService deviceMeasurementIntegrationService,
                                                     FiwareEntityMonitor fiwareEntityMonitor) {
        this.deviceIntegrationService = deviceIntegrationService;
        this.deviceMeasurementIntegrationService = deviceMeasurementIntegrationService;
        this.fiwareEntityMonitor = fiwareEntityMonitor;
    }

    public void persist(Probe probe, List<ProbeData> probeData) {
        persist(probe);
        probeData.forEach(probeDataEntry -> {
            log.info("Persisting measurement for probe: {}", probe);
            var deviceMeasurement = createDeviceMeasurement(probe, probeDataEntry);
            deviceMeasurementIntegrationService.persist(deviceMeasurement);
            fiwareEntityMonitor.entitiesSavedOrUpdated(Manufacturer.SENSOTERRA);
        });
    }

    private void persist(Probe probe) {
        var device = Device.builder()
                .id(FiwareDeviceId.create(Manufacturer.SENSOTERRA, String.valueOf(probe.getId())))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(Manufacturer.SENSOTERRA.key()))
                        .build())
                .build();
        deviceIntegrationService.persist(device);
        fiwareEntityMonitor.sensorsSavedOrUpdated(Manufacturer.SENSOTERRA);
    }

    private DeviceMeasurement createDeviceMeasurement(Probe probe, ProbeData probeData) {
        log.debug("Persisting probe data for probe: {}", probe);
        log.debug("Persisting probe data: {}", probeData);
        return DeviceMeasurement.builder()
                .id(FiwareDevicMeasurementeId.create(Manufacturer.SENSOTERRA))
                .refDevice(FiwareDeviceId.create(Manufacturer.SENSOTERRA, String.valueOf(probe.getId())))
                .dateObserved(InstantFormat.format(probeData.getTimestamp()))
                .location(Location.builder()
                        .coordinates(List.of(probe.getLatitude(), probe.getLongitude()))
                        .build())
                .controlledProperty("value")
                .numValue(probeData.getValue())
                .unit(probeData.getUnit())
                .build();
    }

}
