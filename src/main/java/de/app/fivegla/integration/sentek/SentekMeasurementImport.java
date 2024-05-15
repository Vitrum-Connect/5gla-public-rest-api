package de.app.fivegla.integration.sentek;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.business.LastRunService;
import de.app.fivegla.integration.sentek.model.csv.Reading;
import de.app.fivegla.integration.sentek.model.xml.Logger;
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
import java.util.List;
import java.util.Map;

/**
 * Scheduled data import from Sentek API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SentekMeasurementImport {

    private final SentekSensorDataIntegrationService sentekSensorDataIntegrationService;
    private final LastRunService lastRunService;
    private final SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper;
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
            if (lastRunService.getLastRun(Manufacturer.SENTEK).isPresent()) {
                log.info("Running scheduled data import from Sentek API");
                var lastRun = lastRunService.getLastRun(Manufacturer.SENTEK).get();
                var measurements = sentekSensorDataIntegrationService.fetchAll(thirdPartyApiConfiguration, lastRun);
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.SENTEK, measurements.size());
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                measurements.entrySet().forEach(loggerListEntry -> persistDataWithinFiware(tenant, loggerListEntry));
            } else {
                log.info("Running initial data import from Sentek API, this may take a while");
                var measurements = sentekSensorDataIntegrationService.fetchAll(thirdPartyApiConfiguration, Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.SENTEK, measurements.size());
                measurements.entrySet().forEach(loggerListEntry -> persistDataWithinFiware(tenant, loggerListEntry));
            }
            lastRunService.updateLastRun(Manufacturer.SENTEK);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Sentek API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.SENTEK);
        } finally {
            log.info("Finished scheduled data import from Sentek API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.SENTEK, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    private void persistDataWithinFiware(Tenant tenant, Map.Entry<Logger, List<Reading>> entry) {
        try {
            Logger key = entry.getKey();
            List<Reading> value = entry.getValue();
            sentekFiwareIntegrationServiceWrapper.persist(tenant, key, value);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Sentek API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.SENTEK);
        }
    }

}
