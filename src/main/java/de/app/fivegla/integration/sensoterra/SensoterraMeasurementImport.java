package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.business.LastRunService;
import de.app.fivegla.integration.sensoterra.model.Probe;
import de.app.fivegla.integration.sensoterra.model.ProbeData;
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
 * Scheduled data import from Agvolution API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SensoterraMeasurementImport {

    private final ProbeDataIntegrationService probeDataIntegrationService;
    private final LastRunService lastRunService;
    private final SensoterraFiwareIntegrationServiceWrapper sensoterraFiwareIntegrationServiceWrapper;
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
            var lastRun = lastRunService.getLastRun(Manufacturer.SENSOTERRA);
            if (lastRun.isPresent()) {
                log.info("Running scheduled data import from Sensoterra API");
                var seriesEntries = probeDataIntegrationService.fetchAll(thirdPartyApiConfiguration, lastRun.get());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.SENSOTERRA, seriesEntries.size());
                log.info("Found {} seriesEntries", seriesEntries.size());
                log.info("Persisting {} seriesEntries", seriesEntries.size());
                seriesEntries.entrySet().forEach(probeListEntry -> persistDataWithinFiware(tenant, probeListEntry));
            } else {
                log.info("Running initial data import from Sensoterra API, this may take a while");
                var seriesEntries = probeDataIntegrationService.fetchAll(thirdPartyApiConfiguration, Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
                log.info("Found {} seriesEntries", seriesEntries.size());
                log.info("Persisting {} seriesEntries", seriesEntries.size());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.SENSOTERRA, seriesEntries.size());
                seriesEntries.entrySet().forEach(probeListEntry -> persistDataWithinFiware(tenant, probeListEntry));
            }
            lastRunService.updateLastRun(Manufacturer.SENSOTERRA);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Sensoterra API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.SENSOTERRA);
        } finally {
            log.info("Finished scheduled data import from Sensoterra API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.SENSOTERRA, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    private void persistDataWithinFiware(Tenant tenant, Map.Entry<Probe, List<ProbeData>> entry) {
        try {
            Probe key = entry.getKey();
            List<ProbeData> value = entry.getValue();
            sensoterraFiwareIntegrationServiceWrapper.persist(tenant, key, value);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Sensoterra API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.SENSOTERRA);
        }
    }

}
