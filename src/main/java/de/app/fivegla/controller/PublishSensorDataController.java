package de.app.fivegla.controller;


import de.app.fivegla.api.Tags;
import de.app.fivegla.controller.dto.request.PublishSensorDataRequest;
import de.app.fivegla.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sensor-data")
public class PublishSensorDataController {

    private final SensorService sensorService;

    public PublishSensorDataController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Publish sensor data.
     */
    @Operation(
            operationId = "sensor-data.publish",
            description = "Publish new sensor data.",
            tags = {Tags.SENSOR_DATA},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The request to publish sensor data.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PublishSensorDataRequest.class)
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The sensor data was published successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The request is invalid."
                    )
            }
    )
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> publishSensorData(@Parameter(description = "The request to publish sensor data.") @Valid @RequestBody PublishSensorDataRequest request) {
        sensorService.publishSensorData(request.getSensorId(), request.getBase64EncodedSensorData());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
