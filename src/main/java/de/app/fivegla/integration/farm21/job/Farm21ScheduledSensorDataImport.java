package de.app.fivegla.integration.farm21.job;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.farm21.Farm21SensorDataIntegrationService;
import de.app.fivegla.integration.farm21.fiware.Farm21FiwareIntegrationServiceWrapper;
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
public class Farm21ScheduledSensorDataImport {

    private final Farm21SensorDataIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final Farm21FiwareIntegrationServiceWrapper farm21FiwareIntegrationServiceWrapper;

    public Farm21ScheduledSensorDataImport(Farm21SensorDataIntegrationService farm21SensorDataIntegrationService,
                                           ApplicationDataRepository applicationDataRepository,
                                           Farm21FiwareIntegrationServiceWrapper farm21FiwareIntegrationServiceWrapper) {
        this.soilScoutMeasurementIntegrationService = farm21SensorDataIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.farm21FiwareIntegrationServiceWrapper = farm21FiwareIntegrationServiceWrapper;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.farm21.data-import.cron}}")
    public void run() {
        if (applicationDataRepository.getLastRun(Manufacturer.FARM21).isPresent()) {
            log.info("Running scheduled data import from Farm21 API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.FARM21).get();
            var measurements = soilScoutMeasurementIntegrationService.findAll(lastRun, Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(farm21FiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = soilScoutMeasurementIntegrationService.findAll(Instant.now().minus(14, ChronoUnit.DAYS), Instant.now());
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(farm21FiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun(Manufacturer.FARM21);
    }

}
