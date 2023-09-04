package de.app.fivegla.controller;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.SentekDataLoggingRequest;
import de.app.fivegla.controller.dto.request.WeenatDataLoggingRequest;
import de.app.fivegla.integration.sentek.SentekFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.sentek.SentekSensorIntegrationService;
import de.app.fivegla.integration.weenat.WeenatFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.weenat.WeenatPlotIntegrationService;
import de.app.fivegla.integration.weenat.model.Measurements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The DataLoggingController class handles the logging of Sentek data for a specific sensor.
 */
@Slf4j
@RestController
@RequestMapping(BaseMappings.DATA_LOGGING)
public class DataLoggingController {

    private final SentekSensorIntegrationService sentekSensorIntegrationService;
    private final SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper;
    private final WeenatPlotIntegrationService weenatPlotIntegrationService;
    private final WeenatFiwareIntegrationServiceWrapper weenatFiwareIntegrationServiceWrapper;

    public DataLoggingController(SentekSensorIntegrationService sentekSensorIntegrationService,
                                 SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper,
                                 WeenatPlotIntegrationService weenatPlotIntegrationService,
                                 WeenatFiwareIntegrationServiceWrapper weenatFiwareIntegrationServiceWrapper) {
        this.sentekSensorIntegrationService = sentekSensorIntegrationService;
        this.sentekFiwareIntegrationServiceWrapper = sentekFiwareIntegrationServiceWrapper;
        this.weenatPlotIntegrationService = weenatPlotIntegrationService;
        this.weenatFiwareIntegrationServiceWrapper = weenatFiwareIntegrationServiceWrapper;
    }

    /**
     * This method logs the Sentek data for a specific sensor.
     *
     * @param sensorId The ID of the sensor
     * @param request  The SentekDataLoggingRequest object containing the data readings
     * @return The ResponseEntity object indicating the success or failure of the data logging
     */
    @PostMapping(value = "/sentek/{sensorId}")
    public ResponseEntity<String> sentek(@PathVariable int sensorId, @RequestBody SentekDataLoggingRequest request) {
        AtomicBoolean dataHasBeenLogged = new AtomicBoolean(false);
        sentekSensorIntegrationService.fetchAll().stream().filter(sensor -> sensor.getId() == sensorId).findFirst().ifPresentOrElse(sensor -> {
            log.info("Persisting {} measurements for sensor {}.", request.readings().size(), sensorId);
            sentekFiwareIntegrationServiceWrapper.persist(sensor, request.readings());
            dataHasBeenLogged.set(true);
        }, () -> log.error("No sensor found for id {}.", sensorId));

        if (dataHasBeenLogged.get()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessage.builder()
                            .error(Error.SENTEK_COULD_NOT_FIND_SENSOR_FOR_ID)
                            .message("No sensor found for id " + sensorId)
                            .build()
                            .asDetail());
        }
    }

    /**
     * This method logs the Weenat data for a specific plot.
     *
     * @param plotId  The ID of the plot
     * @param request The WeenatDataLoggingRequest object containing the data measurements
     * @return The ResponseEntity object indicating the success or failure of the data logging
     */
    @PostMapping(value = "/weenat/{plotId}")
    public ResponseEntity<String> weenat(@PathVariable int plotId, @RequestBody WeenatDataLoggingRequest request) {
        AtomicBoolean dataHasBeenLogged = new AtomicBoolean(false);
        weenatPlotIntegrationService.fetchAll().stream().filter(plot -> plot.getId() == plotId).findFirst().ifPresentOrElse(plot -> {
            log.info("Persisting {} measurements for plot {}.", request.measurements().size(), plotId);
            weenatFiwareIntegrationServiceWrapper.persist(plot, Measurements.builder().measurements(request.measurements()).plot(plot).build());
            dataHasBeenLogged.set(true);
        }, () -> log.error("No plot found for id {}.", plotId));

        if (dataHasBeenLogged.get()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessage.builder()
                            .error(Error.WEENAT_COULD_NOT_FIND_SENSOR_FOR_ID)
                            .message("No plot found for id " + plotId)
                            .build()
                            .asDetail());
        }
    }

}
