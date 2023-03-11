package de.app.fivegla.integration.soilscout.job;

import de.app.fivegla.integration.fiware.FiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.soilscout.SoilScoutIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Scheduled data import from Soil Scout API.
 */
@Slf4j
@Service
public class SoilScoutScheduledSensorImport {

    private final SoilScoutIntegrationService soilScoutIntegrationService;
    private final FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;

    public SoilScoutScheduledSensorImport(SoilScoutIntegrationService soilScoutIntegrationService,
                                          FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper) {
        this.soilScoutIntegrationService = soilScoutIntegrationService;
        this.fiwareIntegrationServiceWrapper = fiwareIntegrationServiceWrapper;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.sensor-import.cron}}")
    public void run() {
        log.info("Running scheduled sensor import from Soil Scout API");
        var sensors = soilScoutIntegrationService.findAllSensors();
        log.info("Found {} sensors", sensors.size());
        sensors.forEach(fiwareIntegrationServiceWrapper::createSensor);
    }

}
