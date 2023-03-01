package de.app.fivegla.integration.soilscout.job;

import de.app.fivegla.integration.soilscout.SoilScoutIntegrationService;
import lombok.Getter;
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
public class SoilScoutScheduledDataImport {

    private final SoilScoutIntegrationService soilScoutIntegrationService;

    @Getter
    private Instant lastRun;

    public SoilScoutScheduledDataImport(SoilScoutIntegrationService soilScoutIntegrationService) {
        this.soilScoutIntegrationService = soilScoutIntegrationService;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void run() {
        if (lastRun != null) {
            log.info("Running scheduled data import from Soil Scout API");
            var measurements = soilScoutIntegrationService.findAllMeasurements(lastRun, Instant.now());
            log.info("Found {} measurements", measurements.size());
        } else {
            log.info("Running initial data import from Soil Scout API, this may take a while");
            var measurements = soilScoutIntegrationService.findAllMeasurements(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
        }
        lastRun = Instant.now();
    }

}
