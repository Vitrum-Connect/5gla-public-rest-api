package de.app.fivegla.controller;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.SentekDataLoggingRequest;
import de.app.fivegla.integration.sentek.SentekFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.sentek.SentekSensorIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RestController
@RequestMapping(BaseMappings.DATA_LOGGING)
public class DataLoggingController {

    private final SentekSensorIntegrationService sentekSensorIntegrationService;
    private final SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper;

    public DataLoggingController(SentekSensorIntegrationService sentekSensorIntegrationService,
                                 SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper) {
        this.sentekSensorIntegrationService = sentekSensorIntegrationService;
        this.sentekFiwareIntegrationServiceWrapper = sentekFiwareIntegrationServiceWrapper;
    }

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

}
