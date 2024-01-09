package de.app.fivegla.integration.farm21;

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
 * Scheduled data import from Farm21 API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class Farm21MeasurementImport {

    private final Farm21SensorDataIntegrationService farm21SensorDataIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final Farm21FiwareIntegrationServiceWrapper farm21FiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    /**
     * Run scheduled data import.
     */
    public void run() {
        if (applicationDataRepository.getLastRun(Manufacturer.FARM21).isPresent()) {
            log.info("Running scheduled data import from Farm21 API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.FARM21).get();
            var measurements = farm21SensorDataIntegrationService.fetchAll(lastRun, Instant.now());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.FARM21);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(farm21FiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = farm21SensorDataIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.FARM21);
            measurements.forEach(farm21FiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun(Manufacturer.FARM21);
    }

}
