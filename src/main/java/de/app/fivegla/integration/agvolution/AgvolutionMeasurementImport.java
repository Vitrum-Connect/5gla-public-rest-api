package de.app.fivegla.integration.agvolution;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.agvolution.model.SeriesEntry;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Agvolution API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgvolutionMeasurementImport {

    private final AgvolutionSensorDataIntegrationService agvolutionSensorDataIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final AgvolutionFiwareIntegrationServiceWrapper agvolutionFiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    /**
     * Run scheduled data import.
     */
    @Async
    public void run(String tenantId) {
        var begin = Instant.now();
        try {
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.AGVOLUTION);
            if (lastRun.isPresent()) {
                log.info("Running scheduled data import from Agvolution API");
                var seriesEntries = agvolutionSensorDataIntegrationService.fetchAll(lastRun.get());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGVOLUTION, seriesEntries.size());
                log.info("Found {} seriesEntries", seriesEntries.size());
                log.info("Persisting {} seriesEntries", seriesEntries.size());
                seriesEntries.forEach(this::persistDataWithinFiware);
            } else {
                log.info("Running initial data import from Agvolution API, this may take a while");
                var seriesEntries = agvolutionSensorDataIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
                log.info("Found {} seriesEntries", seriesEntries.size());
                log.info("Persisting {} seriesEntries", seriesEntries.size());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGVOLUTION, seriesEntries.size());
                seriesEntries.forEach(this::persistDataWithinFiware);
            }
            applicationDataRepository.updateLastRun(Manufacturer.AGVOLUTION);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agvolution API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGVOLUTION);
        } finally {
            log.info("Finished scheduled data import from Agvolution API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.AGVOLUTION, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    private void persistDataWithinFiware(SeriesEntry seriesEntry) {
        try {
            agvolutionFiwareIntegrationServiceWrapper.persist(seriesEntry);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agvolution API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGVOLUTION);
        }
    }

}
