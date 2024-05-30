package de.app.fivegla.event;

import de.app.fivegla.api.SubscriptionStatus;
import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.event.events.DataImportEvent;
import de.app.fivegla.integration.agranimo.AgranimoMeasurementImport;
import de.app.fivegla.integration.agvolution.AgvolutionMeasurementImport;
import de.app.fivegla.integration.farm21.Farm21MeasurementImport;
import de.app.fivegla.integration.fiware.SubscriptionIntegrationService;
import de.app.fivegla.integration.sensoterra.SensoterraMeasurementImport;
import de.app.fivegla.integration.sentek.SentekMeasurementImport;
import de.app.fivegla.integration.soilscout.SoilScoutMeasurementImport;
import de.app.fivegla.integration.weenat.WeenatMeasurementImport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for data import events.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataImportEventHandler {

    private final SoilScoutMeasurementImport soilScoutScheduledMeasurementImport;
    private final AgranimoMeasurementImport agranimoMeasurementImport;
    private final AgvolutionMeasurementImport agvolutionMeasurementImport;
    private final Farm21MeasurementImport farm21MeasurementImport;
    private final SensoterraMeasurementImport sensoterraMeasurementImport;
    private final SentekMeasurementImport sentekMeasurementImport;
    private final WeenatMeasurementImport weenatMeasurementImport;
    private final SubscriptionStatus subscriptionStatus;
    private final TenantService tenantService;
    private final SubscriptionIntegrationService subscriptionService;

    @EventListener(DataImportEvent.class)
    public void handleDataImportEvent(DataImportEvent dataImportEvent) {
        log.info("Handling data import event for tenant {} and manufacturer {}.", dataImportEvent.thirdPartyApiConfiguration().getTenant().getTenantId(), dataImportEvent.thirdPartyApiConfiguration().getManufacturer());
        var manufacturer = dataImportEvent.thirdPartyApiConfiguration().getManufacturer();
        var tenantId = dataImportEvent.thirdPartyApiConfiguration().getTenant().getTenantId();
        var optionalTenant = tenantService.findByTenantId(tenantId);
        if (optionalTenant.isEmpty()) {
            log.error("Tenant with id {} not found, not able to handle data import event", tenantId);
        } else {
            var tenant = optionalTenant.get();
            var config = dataImportEvent.thirdPartyApiConfiguration();
            if (subscriptionStatus.sendOutSubscriptions(tenantId)) {
                try {
                    subscriptionService.subscribe(tenant, EntityType.values());
                    log.info("Subscribed to device measurement notifications.");
                    subscriptionStatus.subscriptionSent(tenantId);
                } catch (BusinessException e) {
                    log.error("Could not subscribe to device measurement notifications.", e);
                }
            } else {
                log.info("Subscriptions are disabled. Not subscribing to device measurement notifications.");
            }
            switch (manufacturer) {
                case SOILSCOUT -> soilScoutScheduledMeasurementImport.run(tenant, config);
                case AGVOLUTION -> agvolutionMeasurementImport.run(tenant, config);
                case AGRANIMO -> agranimoMeasurementImport.run(tenant, config);
                case FARM21 -> farm21MeasurementImport.run(tenant, config);
                case SENSOTERRA -> sensoterraMeasurementImport.run(tenant, config);
                case SENTEK -> sentekMeasurementImport.run(tenant, config);
                case WEENAT -> weenatMeasurementImport.run(tenant, config);
                default -> throw new IllegalArgumentException("Unknown manufacturer: " + manufacturer);
            }
        }
    }

}
