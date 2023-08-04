package de.app.fivegla.integration.agranimo.fiware;


import de.app.fivegla.api.Constants;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.integration.soilscout.model.SensorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    private static String getFiwareId(int sensorId) {
        return Constants.FIWARE_AGRANIMO_SENSOR_ID_PREFIX + sensorId;
    }

    private static String getFiwareId() {
        return Constants.FIWARE_AGRANIMO_SENSOR_ID_PREFIX + UUID.randomUUID();
    }

    public void persist(List<SensorData> measurements) {
        log.error("Not implemented yet");
    }
}
