package de.app.fivegla.integration.weenat;

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
 * Scheduled data import from Sentek API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeenatMeasurementImport {

    private final WeenatMeasuresIntegrationService weenatMeasuresIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final WeenatFiwareIntegrationServiceWrapper weenatFiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    /**
     * Run scheduled data import.
     */
    public void run() {
        if (applicationDataRepository.getLastRun(Manufacturer.WEENAT).isPresent()) {
            log.info("Running scheduled data import from Farm21 API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.WEENAT).get();
            var measurements = weenatMeasuresIntegrationService.fetchAll(lastRun);
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.WEENAT);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(weenatFiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = weenatMeasuresIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.WEENAT);
        }
        applicationDataRepository.updateLastRun(Manufacturer.WEENAT);
    }

}
