package de.app.fivegla.integration.agvolution;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.integration.agvolution.model.SeriesEntry;
import de.app.fivegla.integration.agvolution.model.TimeSeriesEntry;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgvolutionFiwareIntegrationServiceWrapper {
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    public void persist(Tenant tenant, Group group, SeriesEntry seriesEntry) {
        seriesEntry.getTimeSeriesEntries().forEach(timeSeriesEntry -> {
            var deviceMeasurements = createDeviceMeasurements(tenant, group, seriesEntry, timeSeriesEntry);
            log.info("Persisting measurement for device: {}", seriesEntry.getDeviceId());
            deviceMeasurements.forEach(deviceMeasurement -> {
                log.info("Persisting measurement: {}", deviceMeasurement);
                fiwareEntityIntegrationService.persist(deviceMeasurement);
            });
        });
    }

    private List<DeviceMeasurement> createDeviceMeasurements(Tenant tenant, Group group, SeriesEntry seriesEntry, TimeSeriesEntry timeSeriesEntry) {
        log.debug("Persisting data for device: {}", seriesEntry.getDeviceId());
        log.debug("Persisting data: {}", timeSeriesEntry);
        var deviceMeasurements = new ArrayList<DeviceMeasurement>();
        timeSeriesEntry.getValues().forEach(timeSeriesValue -> {
            var deviceMeasurement = new DeviceMeasurement(
                    tenant.getFiwarePrefix() + seriesEntry.getDeviceId(),
                    EntityType.AGVOLUTION_SENSOR.getKey(),
                    new TextAttribute(group.getGroupId()),
                    new TextAttribute(timeSeriesEntry.getKey()),
                    new NumberAttribute(timeSeriesValue.getValue()),
                    new DateTimeAttribute(timeSeriesValue.getTime()),
                    new EmptyAttribute(),
                    seriesEntry.getLatitude(),
                    seriesEntry.getLongitude());
            deviceMeasurements.add(deviceMeasurement);
        });
        return deviceMeasurements;
    }

}
