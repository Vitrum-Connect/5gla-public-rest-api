package de.app.fivegla.event;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.event.events.DataImportEvent;
import de.app.fivegla.event.events.HistoricalDataImportEvent;
import de.app.fivegla.integration.agranimo.AgranimoMeasurementImport;
import de.app.fivegla.integration.agvolution.AgvolutionMeasurementImport;
import de.app.fivegla.integration.farm21.Farm21MeasurementImport;
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
    private final TenantService tenantService;
    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;

    @EventListener(DataImportEvent.class)
    public void handleDataImportEvent(DataImportEvent dataImportEvent) {
        var thirdPartyApiConfiguration = thirdPartyApiConfigurationService.findById(dataImportEvent.thirdPartyApiConfigurationId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                        .error(Error.THIRD_PARTY_API_CONFIGURATION_NOT_FOUND)
                        .message("Third party API configuration not found.")
                        .build()));
        log.info("Handling data import event for tenant {} and manufacturer {}.", thirdPartyApiConfiguration.getTenant().getTenantId(), thirdPartyApiConfiguration.getManufacturer());
        var manufacturer = thirdPartyApiConfiguration.getManufacturer();
        var tenantId = thirdPartyApiConfiguration.getTenant().getTenantId();
        var optionalTenant = tenantService.findByTenantId(tenantId);
        if (optionalTenant.isEmpty()) {
            log.error("Tenant with id {} not found, not able to handle data import event", tenantId);
        } else {
            var tenant = optionalTenant.get();
            switch (manufacturer) {
                case SOILSCOUT -> soilScoutScheduledMeasurementImport.run(tenant, thirdPartyApiConfiguration);
                case AGVOLUTION -> agvolutionMeasurementImport.run(tenant, thirdPartyApiConfiguration);
                case AGRANIMO -> agranimoMeasurementImport.run(tenant, thirdPartyApiConfiguration);
                case FARM21 -> farm21MeasurementImport.run(tenant, thirdPartyApiConfiguration);
                case SENSOTERRA -> sensoterraMeasurementImport.run(tenant, thirdPartyApiConfiguration);
                case SENTEK -> sentekMeasurementImport.run(tenant, thirdPartyApiConfiguration);
                case WEENAT -> weenatMeasurementImport.run(tenant, thirdPartyApiConfiguration);
                default -> throw new IllegalArgumentException("Unknown manufacturer: " + manufacturer);
            }
        }
        thirdPartyApiConfigurationService.updateLastRun(thirdPartyApiConfiguration);
    }

    @EventListener(HistoricalDataImportEvent.class)
    public void handleHistoricalDataImportEvent(HistoricalDataImportEvent historicalDataImportEvent) {
        var thirdPartyApiConfiguration = thirdPartyApiConfigurationService.findById(historicalDataImportEvent.thirdPartyApiConfigurationId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                        .error(Error.THIRD_PARTY_API_CONFIGURATION_NOT_FOUND)
                        .message("Third party API configuration not found.")
                        .build()));
        log.info("Handling data import event for tenant {} and manufacturer {}.", thirdPartyApiConfiguration.getTenant().getTenantId(), thirdPartyApiConfiguration.getManufacturer());
        var manufacturer = thirdPartyApiConfiguration.getManufacturer();
        var tenantId = thirdPartyApiConfiguration.getTenant().getTenantId();
        var optionalTenant = tenantService.findByTenantId(tenantId);
        if (optionalTenant.isEmpty()) {
            log.error("Tenant with id {} not found, not able to handle data import event", tenantId);
        } else {
            var tenant = optionalTenant.get();
            switch (manufacturer) {
                case SOILSCOUT -> soilScoutScheduledMeasurementImport.run(tenant, thirdPartyApiConfiguration, historicalDataImportEvent.startDate());
                case AGVOLUTION -> agvolutionMeasurementImport.run(tenant, thirdPartyApiConfiguration, historicalDataImportEvent.startDate());
                case AGRANIMO -> agranimoMeasurementImport.run(tenant, thirdPartyApiConfiguration, historicalDataImportEvent.startDate());
                case FARM21 -> farm21MeasurementImport.run(tenant, thirdPartyApiConfiguration, historicalDataImportEvent.startDate());
                case SENSOTERRA -> sensoterraMeasurementImport.run(tenant, thirdPartyApiConfiguration, historicalDataImportEvent.startDate());
                case SENTEK -> sentekMeasurementImport.run(tenant, thirdPartyApiConfiguration, historicalDataImportEvent.startDate());
                case WEENAT -> weenatMeasurementImport.run(tenant, thirdPartyApiConfiguration, historicalDataImportEvent.startDate());
                default -> throw new IllegalArgumentException("Unknown manufacturer: " + manufacturer);
            }
        }
        thirdPartyApiConfigurationService.updateLastRun(thirdPartyApiConfiguration);
    }

}
