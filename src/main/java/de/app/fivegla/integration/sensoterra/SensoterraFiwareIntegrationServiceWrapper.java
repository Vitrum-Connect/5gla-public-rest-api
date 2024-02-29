package de.app.fivegla.integration.sensoterra;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.SensoterraConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareTypes;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
import de.app.fivegla.integration.sensoterra.model.Probe;
import de.app.fivegla.integration.sensoterra.model.ProbeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SensoterraFiwareIntegrationServiceWrapper {
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    public void persist(Probe probe, List<ProbeData> probeData) {
        probeData.forEach(probeDataEntry -> {
            log.info("Persisting measurement for probe: {}", probe);
            var deviceMeasurement = createDeviceMeasurement(probe, probeDataEntry);
            deviceMeasurementIntegrationService.persist(deviceMeasurement);
        });
    }

    private DeviceMeasurement createDeviceMeasurement(Probe probe, ProbeData probeData) {
        log.debug("Persisting probe data for probe: {}", probe);
        log.debug("Persisting probe data: {}", probeData);
        return new DeviceMeasurementBuilder()
                .withId(getManufacturerConfiguration() + ":" + probe.getId())
                .withType(MeasurementType.SENSOTERRA_SENSOR.name())
                .withLocation(probe.getLatitude(), probe.getLongitude())
                .withMeasurement("value", FiwareTypes.TEXT.getKey(), String.valueOf(probeData.getValue()), probeData.getTimestamp())
                .build();
    }

    private SensoterraConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().sensoterra();
    }

}
