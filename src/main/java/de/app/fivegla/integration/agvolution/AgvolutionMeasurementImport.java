package de.app.fivegla.integration.agvolution;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Agvolution API.
 */
@Slf4j
@Service
public class AgvolutionMeasurementImport {

    private final AgvolutionSensorDataIntegrationService agvolutionSensorDataIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final AgvolutionFiwareIntegrationServiceWrapper agvolutionFiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    public AgvolutionMeasurementImport(AgvolutionSensorDataIntegrationService agvolutionSensorDataIntegrationService,
                                       ApplicationDataRepository applicationDataRepository,
                                       AgvolutionFiwareIntegrationServiceWrapper agvolutionFiwareIntegrationServiceWrapper,
                                       JobMonitor jobMonitor) {
        this.agvolutionSensorDataIntegrationService = agvolutionSensorDataIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.agvolutionFiwareIntegrationServiceWrapper = agvolutionFiwareIntegrationServiceWrapper;
        this.jobMonitor = jobMonitor;
    }

    /**
     * Run scheduled data import.
     */
    public void run() {
        var lastRun = applicationDataRepository.getLastRun(Manufacturer.AGVOLUTION);
        if (lastRun.isPresent()) {
            log.info("Running scheduled data import from Farm21 API");
            var seriesEntries = agvolutionSensorDataIntegrationService.fetchAll(lastRun.get());
            jobMonitor.nrOfEntitiesFetched(seriesEntries.size(), Manufacturer.AGVOLUTION);
            log.info("Found {} seriesEntries", seriesEntries.size());
            log.info("Persisting {} seriesEntries", seriesEntries.size());
            seriesEntries.forEach(agvolutionFiwareIntegrationServiceWrapper::persist);
        } else {
            log.info("Running initial data import from Farm21 API, this may take a while");
            var measurements = agvolutionSensorDataIntegrationService.fetchAll(Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
            log.info("Found {} measurements", measurements.size());
            log.info("Persisting {} measurements", measurements.size());
            jobMonitor.nrOfEntitiesFetched(measurements.size(), Manufacturer.AGVOLUTION);
            measurements.forEach(agvolutionFiwareIntegrationServiceWrapper::persist);
        }
        applicationDataRepository.updateLastRun(Manufacturer.AGVOLUTION);
    }

}
