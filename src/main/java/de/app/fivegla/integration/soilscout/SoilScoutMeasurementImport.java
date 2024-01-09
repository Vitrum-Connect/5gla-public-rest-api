package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.soilscout.model.SensorData;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SoilScoutMeasurementImport {

    private final SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final SoilScoutFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    /**
     * Run scheduled data import.
     */
    public void run() {
        try {
            if (applicationDataRepository.getLastRun(Manufacturer.SOILSCOUT).isPresent()) {
                log.info("Running scheduled data import from Soil Scout API");
                var lastRun = applicationDataRepository.getLastRun(Manufacturer.SOILSCOUT).get();
                var measurements = soilScoutMeasurementIntegrationService.fetchAll(lastRun, Instant.now());
                jobMonitor.logNrOfEntitiesFetched(measurements.size(), Manufacturer.SOILSCOUT);
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                measurements.forEach(this::persistDataWithinFiware);
            } else {
                log.info("Running initial data import from Soil Scout API, this may take a while");
                var measurements = soilScoutMeasurementIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS), Instant.now());
                jobMonitor.logNrOfEntitiesFetched(measurements.size(), Manufacturer.SOILSCOUT);
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                measurements.forEach(this::persistDataWithinFiware);
            }
            applicationDataRepository.updateLastRun(Manufacturer.SOILSCOUT);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Soil Scout API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.SOILSCOUT);
        }
    }

    private void persistDataWithinFiware(SensorData measurement) {
        fiwareIntegrationServiceWrapper.persist(measurement);
    }

}
