package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.integration.agranimo.model.SoilMoisture;
import de.app.fivegla.integration.agranimo.model.Zone;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
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
public class AgranimoMeasurementImport {

    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;
    private final AgranimoFiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;
    private final AgranimoSoilMoistureIntegrationService agranimoSoilMoistureIntegrationService;
    private final AgranimoZoneService agranimoZoneService;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    @Async
    public void run(Tenant tenant, ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        var begin = Instant.now();
        try {
            if (null != thirdPartyApiConfiguration.getLastRun()) {
                log.info("Running scheduled data import from Agranimo API");
                agranimoZoneService.fetchZones(thirdPartyApiConfiguration).forEach(zone -> {
                    var waterContent = agranimoSoilMoistureIntegrationService.fetchWaterContent(thirdPartyApiConfiguration, zone, thirdPartyApiConfiguration.getLastRun().toInstant());
                    jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGRANIMO, waterContent.size());
                    log.info("Found {} water content entries", waterContent.size());
                    log.info("Persisting {} water content entries", waterContent.size());
                    waterContent.forEach(
                            soilMoisture -> persistDataWithinFiware(tenant, zone, soilMoisture)
                    );
                });

            } else {
                log.info("Running scheduled data import from Agranimo API");
                agranimoZoneService.fetchZones(thirdPartyApiConfiguration).forEach(zone -> {
                    var waterContent = agranimoSoilMoistureIntegrationService.fetchWaterContent(thirdPartyApiConfiguration, zone, Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
                    jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGRANIMO, waterContent.size());
                    log.info("Found {} water content entries", waterContent.size());
                    log.info("Persisting {} water content entries", waterContent.size());
                    waterContent.forEach(
                            soilMoisture -> persistDataWithinFiware(tenant, zone, soilMoisture)
                    );
                });
            }
            thirdPartyApiConfigurationService.updateLastRun(thirdPartyApiConfiguration);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agranimo API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGRANIMO);
        } finally {
            log.info("Finished scheduled data import from Agranimo API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.AGRANIMO, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    private void persistDataWithinFiware(Tenant tenant, Zone zone, SoilMoisture soilMoisture) {
        try {
            fiwareIntegrationServiceWrapper.persist(tenant, zone, soilMoisture);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agranimo API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGRANIMO);
        }
    }

}
