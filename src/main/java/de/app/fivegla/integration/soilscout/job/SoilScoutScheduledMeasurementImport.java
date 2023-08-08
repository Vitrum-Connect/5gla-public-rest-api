package de.app.fivegla.integration.soilscout.job;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.soilscout.SoilScoutMeasurementIntegrationService;
import de.app.fivegla.integration.soilscout.fiware.SoilScoutFiwareIntegrationServiceWrapper;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Soil Scout API.
 */
@Slf4j
@Service
public class SoilScoutScheduledMeasurementImport {

    private final SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final SoilScoutFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    public SoilScoutScheduledMeasurementImport(SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService,
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
    @Scheduled(cron = "${app.scheduled.soil-scout.data-import.cron}}")
    public void run() {
        jobMonitor.incNrOfRuns(Manufacturer.SOIL_SCOUT);
        if (applicationDataRepository.getLastRun(Manufacturer.SOIL_SCOUT).isPresent()) {
            log.info("Running scheduled data import from Soil Scout API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.SOIL_SCOUT).get();
            var measurements = soilScoutMeasurementIntegrationService.findAll(lastRun, Instant.now());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.SOIL_SCOUT);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            fiwareIntegrationServiceWrapper.persist(measurements);
        } else {
            log.info("Running initial data import from Soil Scout API, this may take a while");
            var measurements = soilScoutMeasurementIntegrationService.findAll(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.SOIL_SCOUT);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            fiwareIntegrationServiceWrapper.persist(measurements);
        }
        applicationDataRepository.updateLastRun(Manufacturer.SOIL_SCOUT);
    }

}
