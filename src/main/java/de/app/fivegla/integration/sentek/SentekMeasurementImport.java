package de.app.fivegla.integration.sentek;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Sentek API.
 */
@Slf4j
@Service
public class SentekMeasurementImport {

    private final SentekSensorDataIntegrationService sentekSensorDataIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    public SentekMeasurementImport(SentekSensorDataIntegrationService sentekSensorDataIntegrationService,
                                   ApplicationDataRepository applicationDataRepository,
                                   SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper,
                                   JobMonitor jobMonitor) {
        this.sentekSensorDataIntegrationService = sentekSensorDataIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.sentekFiwareIntegrationServiceWrapper = sentekFiwareIntegrationServiceWrapper;
        this.jobMonitor = jobMonitor;
    }

    /**
     * Run scheduled data import.
     */
    public void run() {
        jobMonitor.incNrOfRuns(Manufacturer.SENTEK);
        if (applicationDataRepository.getLastRun(Manufacturer.SENTEK).isPresent()) {
            log.info("Running scheduled data import from Farm21 API");
            var lastRun = applicationDataRepository.getLastRun(Manufacturer.SENTEK).get();
            var measurements = sentekSensorDataIntegrationService.fetchAll(lastRun);
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.SENTEK);
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            measurements.forEach(sentekFiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = sentekSensorDataIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.SENTEK);
            measurements.forEach(sentekFiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun(Manufacturer.SENTEK);
    }

}
