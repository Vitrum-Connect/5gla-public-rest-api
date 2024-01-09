package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Agvolution API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SensoterraMeasurementImport {

    private final ProbeDataIntegrationService probeDataIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final SensoterraFiwareIntegrationServiceWrapper sensoterraFiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    /**
     * Run scheduled data import.
     */
    public void run() {
        var lastRun = applicationDataRepository.getLastRun(Manufacturer.SENSOTERRA);
        if (lastRun.isPresent()) {
            log.info("Running scheduled data import from Farm21 API");
            var seriesEntries = probeDataIntegrationService.fetchAll(lastRun.get());
            jobMonitor.nrOfEntitiesFetched(seriesEntries.size(), Manufacturer.SENSOTERRA);
            log.info("Found {} seriesEntries", seriesEntries.size());
            log.info("Persisting {} seriesEntries", seriesEntries.size());
            seriesEntries.forEach(sensoterraFiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = probeDataIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.SENSOTERRA);
            measurements.forEach(sensoterraFiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun(Manufacturer.SENSOTERRA);
    }

}
