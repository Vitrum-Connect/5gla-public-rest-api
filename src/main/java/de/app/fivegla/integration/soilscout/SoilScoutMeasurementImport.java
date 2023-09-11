package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Soil Scout API.
 */
@Slf4j
@Service
public class SoilScoutMeasurementImport {

    private final SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final SoilScoutFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    public SoilScoutMeasurementImport(SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService,
                                      ApplicationDataRepository applicationDataRepository,
                                      SoilScoutFiwareIntegrationServiceWrapper soilScoutFiwareIntegrationServiceWrapper,
                                      JobMonitor jobMonitor) {
        this.soilScoutMeasurementIntegrationService = soilScoutMeasurementIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.fiwareIntegrationServiceWrapper = soilScoutFiwareIntegrationServiceWrapper;
        this.jobMonitor = jobMonitor;
    }

    /**
     * Run scheduled data import.
     */
    public void run() {
        jobMonitor.incNrOfRuns(Manufacturer.SOILSCOUT);
        if (applicationDataRepository.getLastRun(Manufacturer.SOILSCOUT).isPresent()) {
            log.info("Running scheduled data import from Soil Scout API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.SOILSCOUT).get();
            var measurements = soilScoutMeasurementIntegrationService.fetchAll(lastRun, Instant.now());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.SOILSCOUT);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            fiwareIntegrationServiceWrapper.persist(measurements);
        } else {
            log.info("Running initial data import from Soil Scout API, this may take a while");
            var measurements = soilScoutMeasurementIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS), Instant.now());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.SOILSCOUT);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            fiwareIntegrationServiceWrapper.persist(measurements);
        }
        applicationDataRepository.updateLastRun(Manufacturer.SOILSCOUT);
    }

}
