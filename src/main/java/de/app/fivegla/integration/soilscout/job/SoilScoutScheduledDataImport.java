package de.app.fivegla.integration.soilscout.job;

import de.app.fivegla.integration.soilscout.SoilScoutIntegrationService;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Soil Scout API.
 */
@Slf4j
@Service
@Scope("singleton")
public class SoilScoutScheduledDataImport {

    private final SoilScoutIntegrationService soilScoutIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;

    public SoilScoutScheduledDataImport(SoilScoutIntegrationService soilScoutIntegrationService,
                                        ApplicationDataRepository applicationDataRepository) {
        this.soilScoutIntegrationService = soilScoutIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.data-import.cron}}")
    public void run() {
        if (applicationDataRepository.getLastRun() != null) {
            log.info("Running scheduled data import from Soil Scout API");
            var measurements = soilScoutIntegrationService.findAllMeasurements(applicationDataRepository.getLastRun(), Instant.now());
            log.info("Found {} measurements", measurements.size());
        } else {
            log.info("Running initial data import from Soil Scout API, this may take a while");
            var measurements = soilScoutIntegrationService.findAllMeasurements(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
        }
        applicationDataRepository.updateLastRun();
    }

}
