package de.app.fivegla.integration.weenat;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.business.LastRunService;
import de.app.fivegla.integration.weenat.model.Measurements;
import de.app.fivegla.integration.weenat.model.Plot;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Scheduled data import from Sentek API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeenatMeasurementImport {

    private final WeenatMeasuresIntegrationService weenatMeasuresIntegrationService;
    private final LastRunService lastRunService;
    private final WeenatFiwareIntegrationServiceWrapper weenatFiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    /**
     * Run scheduled data import.
     */
    @Async
    public void run(Tenant tenant, ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        var begin = Instant.now();
        try {
            if (lastRunService.getLastRun(Manufacturer.WEENAT).isPresent()) {
                log.info("Running scheduled data import from Weenat API");
                var lastRun = lastRunService.getLastRun(Manufacturer.WEENAT).get();
                var measurements = weenatMeasuresIntegrationService.fetchAll(thirdPartyApiConfiguration, lastRun);
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.WEENAT, measurements.size());
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                measurements.entrySet().forEach(plotMeasurementsEntry -> persistDataWithinFiware(tenant, plotMeasurementsEntry));
            } else {
                log.info("Running initial data import from Weenat API, this may take a while");
                var measurements = weenatMeasuresIntegrationService.fetchAll(thirdPartyApiConfiguration, Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.WEENAT, measurements.size());
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                measurements.entrySet().forEach(plotMeasurementsEntry -> persistDataWithinFiware(tenant, plotMeasurementsEntry));
            }
            lastRunService.updateLastRun(Manufacturer.WEENAT);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Weenat API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.WEENAT);
        } finally {
            log.info("Finished scheduled data import from Weenat API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.WEENAT, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    private void persistDataWithinFiware(Tenant tenant, Map.Entry<Plot, Measurements> entry) {
        try {
            Plot key = entry.getKey();
            Measurements value = entry.getValue();
            weenatFiwareIntegrationServiceWrapper.persist(tenant, key, value);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Weenat API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.WEENAT);
        }
    }

}
