package de.app.fivegla.scheduled;

import de.app.fivegla.business.TenantService;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.event.events.DataImportEvent;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Scheduled data import for all manufacturers.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataImportScheduler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;
    private final TenantService tenantService;

    /**
     * Schedule data import for all manufacturer.
     */
    @Scheduled(initialDelayString = "${app.scheduled.data-import.initial-delay}", fixedDelayString = "${app.scheduled.data-import.delay}")
    public void scheduleDataImport() {
        log.info("Scheduled data import started for all third-party APIs.");
        List<Tenant> tenants = tenantService.findAll();
        if (null == tenants || tenants.isEmpty()) {
            log.info("No tenants found. Skipping data import.");
        } else {
            tenants.forEach(tenant -> thirdPartyApiConfigurationService.getThirdPartyApiConfigurations(tenant.getTenantId()).forEach(configuration -> {
                if (configuration.isEnabled()) {
                    applicationEventPublisher.publishEvent(new DataImportEvent(configuration));
                } else {
                    log.info("Skipping data import for tenant {} and manufacturer {} because it is disabled.", tenant.getName(), configuration.getManufacturer());
                }
            }));
        }
    }

}
