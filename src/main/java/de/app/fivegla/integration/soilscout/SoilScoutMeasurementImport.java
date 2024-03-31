package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.soilscout.model.SensorData;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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
    @Async
    public void run(Tenant tenant) {
        var begin = Instant.now();
        try {
            if (applicationDataRepository.getLastRun(Manufacturer.SOILSCOUT).isPresent()) {
                log.info("Running scheduled data import from Soil Scout API");
                var lastRun = applicationDataRepository.getLastRun(Manufacturer.SOILSCOUT).get();
                var measurements = soilScoutMeasurementIntegrationService.fetchAll(lastRun, Instant.now());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.SOILSCOUT, measurements.size());
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                measurements.forEach(measurement -> persistDataWithinFiware(tenant, measurement));
            } else {
                log.info("Running initial data import from Soil Scout API, this may take a while");
                var measurements = soilScoutMeasurementIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS), Instant.now());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.SOILSCOUT, measurements.size());
                log.info("Found {} measurements", measurements.size());
                log.info("Persisting {} measurements", measurements.size());
                measurements.forEach(measurement -> persistDataWithinFiware(tenant, measurement));
            }
            applicationDataRepository.updateLastRun(Manufacturer.SOILSCOUT);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Soil Scout API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.SOILSCOUT);
        } finally {
            log.info("Finished scheduled data import from Soil Scout API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.SOILSCOUT, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    private void persistDataWithinFiware(Tenant tenant, SensorData measurement) {
        fiwareIntegrationServiceWrapper.persist(tenant, measurement);
    }

}
