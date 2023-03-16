package de.app.fivegla.integration.soilscout.job;

import de.app.fivegla.integration.fiware.FiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.soilscout.MeasurementIntegrationService;
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
public class ScheduledMeasurementImport {

    private final MeasurementIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;

    public ScheduledMeasurementImport(MeasurementIntegrationService soilScoutMeasurementIntegrationService,
                                      ApplicationDataRepository applicationDataRepository,
                                      FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper) {
        this.soilScoutMeasurementIntegrationService = soilScoutMeasurementIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.fiwareIntegrationServiceWrapper = fiwareIntegrationServiceWrapper;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.data-import.cron}}")
    public void run() {
        if (applicationDataRepository.getLastRun() != null) {
            log.info("Running scheduled data import from Soil Scout API");
            var measurements = soilScoutMeasurementIntegrationService.findAll(applicationDataRepository.getLastRun(), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(fiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Soil Scout API, this may take a while");
            var measurements = soilScoutMeasurementIntegrationService.findAll(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(fiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun();
    }

}
