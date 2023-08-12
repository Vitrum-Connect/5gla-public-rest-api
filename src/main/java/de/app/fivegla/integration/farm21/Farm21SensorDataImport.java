package de.app.fivegla.integration.farm21;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Farm21 API.
 */
@Slf4j
@Service
public class Farm21SensorDataImport {

    private final Farm21SensorDataIntegrationService farm21SensorDataIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final Farm21FiwareIntegrationServiceWrapper farm21FiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    public Farm21SensorDataImport(Farm21SensorDataIntegrationService farm21SensorDataIntegrationService,
                                  ApplicationDataRepository applicationDataRepository,
                                  Farm21FiwareIntegrationServiceWrapper farm21FiwareIntegrationServiceWrapper,
                                  JobMonitor jobMonitor) {
        this.farm21SensorDataIntegrationService = farm21SensorDataIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.farm21FiwareIntegrationServiceWrapper = farm21FiwareIntegrationServiceWrapper;
        this.jobMonitor = jobMonitor;
    }

    /**
     * Run scheduled data import.
     */
    public void run() {
        jobMonitor.incNrOfRuns(Manufacturer.FARM21);
        if (applicationDataRepository.getLastRun(Manufacturer.FARM21).isPresent()) {
            log.info("Running scheduled data import from Farm21 API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.FARM21).get();
            var measurements = farm21SensorDataIntegrationService.findAll(lastRun, Instant.now());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.FARM21);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(farm21FiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = farm21SensorDataIntegrationService.findAll(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.FARM21);
            measurements.forEach(farm21FiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun(Manufacturer.FARM21);
    }

}
