package de.app.fivegla.integration.agranimo;


import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.integration.soilscout.model.SensorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AgranimoFiwareIntegrationServiceWrapper {
    private final DeviceIntegrationService deviceIntegrationService;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    public AgranimoFiwareIntegrationServiceWrapper(DeviceIntegrationService deviceIntegrationService,
                                                   DeviceMeasurementIntegrationService deviceMeasurementIntegrationService) {
        this.deviceIntegrationService = deviceIntegrationService;
        this.deviceMeasurementIntegrationService = deviceMeasurementIntegrationService;
    }

    public void persist(List<SensorData> measurements) {
        log.error("Not implemented yet");
    }
}
