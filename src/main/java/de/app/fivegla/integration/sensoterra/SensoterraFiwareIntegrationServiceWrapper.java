package de.app.fivegla.integration.sensoterra;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.NumberAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.sensoterra.model.Probe;
import de.app.fivegla.integration.sensoterra.model.ProbeData;
import de.app.fivegla.persistence.entity.Tenant;
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

    public void persist(Tenant tenant, Probe probe, List<ProbeData> probeData) {
        probeData.forEach(probeDataEntry -> {
            log.info("Persisting measurement for probe: {}", probe);
            var deviceMeasurement = createDeviceMeasurement(tenant, probe, probeDataEntry);
            deviceMeasurementIntegrationService.persist(deviceMeasurement);
        });
    }

    private DeviceMeasurement createDeviceMeasurement(Tenant tenant, Probe probe, ProbeData probeData) {
        log.debug("Persisting probe data for probe: {}", probe);
        log.debug("Persisting probe data: {}", probeData);
        return new DeviceMeasurement(tenant.getFiwarePrefix() + probe.getId(),
                EntityType.SENSOTERRA_SENSOR.getKey(),
                new TextAttribute("value"),
                new NumberAttribute(probeData.getValue()),
                new DateTimeAttribute(probeData.getTimestamp()),
                new EmptyAttribute(),
                probe.getLatitude(),
                probe.getLongitude());
    }

}
