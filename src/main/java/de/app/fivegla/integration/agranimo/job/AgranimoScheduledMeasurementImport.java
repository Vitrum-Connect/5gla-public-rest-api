package de.app.fivegla.integration.agranimo.job;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.agranimo.fiware.AgranimoFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.soilscout.SoilScoutMeasurementIntegrationService;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Scheduled data import from Soil Scout API.
 */
@Slf4j
@Service
public class AgranimoScheduledMeasurementImport {

    private final SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService;
    private final ApplicationDataRepository applicationDataRepository;
    private final AgranimoFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    public AgranimoScheduledMeasurementImport(SoilScoutMeasurementIntegrationService soilScoutMeasurementIntegrationService,
                                              ApplicationDataRepository applicationDataRepository,
                                              AgranimoFiwareIntegrationServiceWrapper agranimoFiwareIntegrationServiceWrapper,
                                              JobMonitor jobMonitor) {
        this.soilScoutMeasurementIntegrationService = soilScoutMeasurementIntegrationService;
        this.applicationDataRepository = applicationDataRepository;
        this.fiwareIntegrationServiceWrapper = agranimoFiwareIntegrationServiceWrapper;
        this.jobMonitor = jobMonitor;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.agranimo.data-import.cron}}")
    public void run() {
        if (applicationDataRepository.getLastRun(Manufacturer.AGRANIMO).isPresent()) {
            jobMonitor.nrOfEntitiesFetched(0, Manufacturer.AGRANIMO);
        } else {
            jobMonitor.nrOfEntitiesFetched(0, Manufacturer.AGRANIMO);
        }
        applicationDataRepository.updateLastRun(Manufacturer.AGRANIMO);
    }

}
