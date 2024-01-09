package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        if (applicationDataRepository.getLastRun(Manufacturer.AGRANIMO).isPresent()) {
            jobMonitor.nrOfEntitiesFetched(0, Manufacturer.AGRANIMO);
        } else {
            jobMonitor.nrOfEntitiesFetched(0, Manufacturer.AGRANIMO);
        }
        applicationDataRepository.updateLastRun(Manufacturer.AGRANIMO);
    }

}
