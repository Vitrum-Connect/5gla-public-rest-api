package de.app.fivegla.integration.agranimo.job;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.agranimo.fiware.AgranimoFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.soilscout.SoilScoutMeasurementIntegrationService;
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
public class AgranimoScheduledMeasurementImport {

    private final SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final AgranimoFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;

    public AgranimoScheduledMeasurementImport(SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService,
                                              ApplicationDataRepository applicationDataRepository,
                                              AgranimoFiwareIntegrationServiceWrapper agranimoFiwareIntegrationServiceWrapper) {
        this.soilScoutMeasurementIntegrationService = soilScoutMeasurementIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.fiwareIntegrationServiceWrapper = agranimoFiwareIntegrationServiceWrapper;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.agranimo.data-import.cron}}")
    public void run() {
        if (applicationDataRepository.getLastRun(Manufacturer.AGRANIMO).isPresent()) {
            log.info("Running scheduled data import from Agranimo API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.AGRANIMO).get();
            var measurements = soilScoutMeasurementIntegrationService.findAll(lastRun, Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            fiwareIntegrationServiceWrapper.persist(measurements);
        } else {
            log.info("Running initial data import from Agranimo API, this may take a while");
            var measurements = soilScoutMeasurementIntegrationService.findAll(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            fiwareIntegrationServiceWrapper.persist(measurements);
        }
        applicationDataRepository.updateLastRun(Manufacturer.AGRANIMO);
    }

}
