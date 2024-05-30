package de.app.fivegla.integration.sensoterra;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.InstantAttribute;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.integration.sensoterra.model.Probe;
import de.app.fivegla.integration.sensoterra.model.ProbeData;
import de.app.fivegla.persistence.entity.Group;
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

    private final GroupService groupService;
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    public void persist(Tenant tenant, Probe probe, List<ProbeData> probeData) {
        var group = groupService.findGroupByTenantAndSensorId(tenant, String.valueOf(probe.getId()));
        if (group.isDefaultGroupForTenant()) {
            log.warn("Looks like the group for the sensor with id {} is not set. We are using the default group for the tenant.", probe.getId());
        }
        probeData.forEach(probeDataEntry -> {
            log.info("Persisting measurement for probe: {}", probe);
            var deviceMeasurement = createDeviceMeasurement(tenant, group, probe, probeDataEntry);
            fiwareEntityIntegrationService.persist(tenant, group, deviceMeasurement);
        });
    }

    private DeviceMeasurement createDeviceMeasurement(Tenant tenant, Group group, Probe probe, ProbeData probeData) {
        log.debug("Persisting probe data for probe: {}", probe);
        log.debug("Persisting probe data: {}", probeData);
        return new DeviceMeasurement(tenant.getFiwarePrefix() + probe.getId(),
                EntityType.SENSOTERRA_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("value"),
                new NumberAttribute(probeData.getValue()),
                new InstantAttribute(probeData.getTimestamp()),
                new EmptyAttribute(),
                probe.getLatitude(),
                probe.getLongitude());
    }

}
