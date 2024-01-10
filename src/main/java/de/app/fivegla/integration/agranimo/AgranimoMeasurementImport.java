package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Soil Scout API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgranimoMeasurementImport {

    private final ApplicationDataRepository applicationDataRepository;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final AgranimoFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    public void run() {
        var begin = Instant.now();
        try {
            if (applicationDataRepository.getLastRun(Manufacturer.AGRANIMO).isPresent()) {
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGRANIMO, 0);
            } else {
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGRANIMO, 0);
            }
            applicationDataRepository.updateLastRun(Manufacturer.AGRANIMO);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agranimo API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGRANIMO);
        } finally {
            log.info("Finished scheduled data import from Agranimo API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.AGRANIMO, begin.until(end, ChronoUnit.SECONDS));
        }
    }

}
