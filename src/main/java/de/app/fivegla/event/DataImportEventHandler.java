package de.app.fivegla.event;

import de.app.fivegla.api.SubscriptionStatus;
import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.fiware.SubscriptionService;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
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

import java.util.Arrays;

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
    private final SubscriptionService subscriptionService;
    private final SubscriptionStatus subscriptionStatus;

    @EventListener(DataImportEvent.class)
    public void handleDataImportEvent(DataImportEvent dataImportEvent) {
        log.info("Handling data import event for manufacturer {}.", dataImportEvent.manufacturer());
        if (subscriptionStatus.sendOutSubscriptions()) {
            try {
                subscriptionService.subscribe(Arrays.stream(MeasurementType.values()).map(Enum::name).toArray(String[]::new));
                log.info("Subscribed to device measurement notifications.");
                subscriptionStatus.setSubscriptionsSent(true);
            } catch (FiwareIntegrationLayerException e) {
                log.error("Could not subscribe to device measurement notifications.");
            }
        } else {
            log.info("Subscriptions are disabled. Not subscribing to device measurement notifications.");
        }
        switch (dataImportEvent.manufacturer()) {
            case SOILSCOUT -> soilScoutScheduledMeasurementImport.run();
            case AGVOLUTION -> agvolutionMeasurementImport.run();
            case AGRANIMO -> agranimoMeasurementImport.run();
            case FARM21 -> farm21MeasurementImport.run();
            case SENSOTERRA -> sensoterraMeasurementImport.run();
            case SENTEK -> sentekMeasurementImport.run();
            case WEENAT -> weenatMeasurementImport.run();
            case MICA_SENSE -> log.info("There is no scheduled data import for MicaSense");
            default -> throw new IllegalArgumentException("Unknown manufacturer: " + dataImportEvent.manufacturer());
        }
    }
}
