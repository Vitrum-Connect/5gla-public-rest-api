package de.app.fivegla.event;

import de.app.fivegla.integration.agranimo.AgranimoMeasurementImport;
import de.app.fivegla.integration.agvolution.AgvolutionMeasurementImport;
import de.app.fivegla.integration.farm21.Farm21SensorDataImport;
import de.app.fivegla.integration.soilscout.SoilScoutMeasurementImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for data import events.
 */
@Slf4j
@Component
public class DataImportEventHandler {

    private final SoilScoutMeasurementImport soilScoutScheduledMeasurementImport;
    private final AgranimoMeasurementImport agranimoScheduledMeasurementImport;
    private final AgvolutionMeasurementImport agvolutionMeasurementImport;
    private final Farm21SensorDataImport farm21SensorDataImport;

    public DataImportEventHandler(SoilScoutMeasurementImport soilScoutMeasurementImport,
                                  AgranimoMeasurementImport agranimoMeasurementImport,
                                  AgvolutionMeasurementImport agvolutionMeasurementImport,
                                  Farm21SensorDataImport farm21SensorDataImport) {
        this.soilScoutScheduledMeasurementImport = soilScoutMeasurementImport;
        this.agranimoScheduledMeasurementImport = agranimoMeasurementImport;
        this.agvolutionMeasurementImport = agvolutionMeasurementImport;
        this.farm21SensorDataImport = farm21SensorDataImport;
    }

    @EventListener(DataImportEvent.class)
    public void handleDataImportEvent(DataImportEvent dataImportEvent) {
        switch (dataImportEvent.manufacturer()) {
            case SOIL_SCOUT -> soilScoutScheduledMeasurementImport.run();
            case AGVOLUTION -> agvolutionMeasurementImport.run();
            case AGRANIMO -> agranimoScheduledMeasurementImport.run();
            case FARM21 -> farm21SensorDataImport.run();
            case MICA_SENSE -> log.info("There is no scheduled data import for MicaSense");
            default -> throw new IllegalArgumentException("Unknown manufacturer: " + dataImportEvent.manufacturer());
        }
    }
}
