package de.app.fivegla.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for sensor data.
 */
@Slf4j
@RestController
@RequestMapping("sensor-data")
public class SensorDataController {

    /**
     * Publishes the sensor data to the 5GLA environment.
     *
     * @param sensorData the sensor data
     * @return the response entity
     */
    @Operation(
            operationId = "sensor-data.publish",
            description = "Publish sensor data to the 5GLA environment."
    )
    @ApiResponse(
            responseCode = "200",
            description = "The sensor data was successfully published."
    )
    @PostMapping(value = "/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> publish(@RequestBody String sensorData) {
        log.debug("Received sensor data: {}", sensorData);
        return ResponseEntity.ok().build();
    }

}
