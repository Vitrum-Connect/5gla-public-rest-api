package de.app.fivegla.controller;

import de.app.fivegla.api.Tags;
import de.app.fivegla.controller.dto.request.CreateSensorDataRequest;
import de.app.fivegla.controller.dto.response.SensorDataDto;
import de.app.fivegla.controller.dto.response.SensorDataResponse;
import de.app.fivegla.domain.SensorMasterData;
import de.app.fivegla.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for sensor master data.
 */
@RestController
@RequestMapping("sensor-master-data")
public class SensorMasterDataController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    public SensorMasterDataController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a new sensor.
     */
    @Operation(
            operationId = "sensor-master-data.create",
            description = "Create a new sensor.",
            tags = {Tags.SENSOR_MASTER_DATA},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The request to create a sensor.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateSensorDataRequest.class)
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The sensor was successfully created."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The request is invalid."
                    )
            }
    )
    @PostMapping(value = "/create")
    public ResponseEntity<Void> create(@Parameter(description = "The request to create a sensor.") @Valid @RequestBody CreateSensorDataRequest createSensorDataRequest) {
        var sensorData = SensorMasterData.builder()
                .sensorId(createSensorDataRequest.getSensorId())
                .build();
        sensorService.createSensor(sensorData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Get all sensors.
     */
    @Operation(
            operationId = "sensor-master-data.fetch-all",
            description = "Get all sensors.",
            tags = {Tags.SENSOR_MASTER_DATA}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The sensors were successfully retrieved.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = SensorDataResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The request is invalid."
                    )
            }
    )
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<SensorDataResponse> findAll() {
        var sensorDataDtos = sensorService.findAll().stream().map(sensorData -> modelMapper.map(sensorData, SensorDataDto.class)).toList();
        return ResponseEntity.ok(SensorDataResponse.builder().sensorData(sensorDataDtos).build());
    }

    /**
     * Get sensor by id.
     */
    @Operation(
            operationId = "sensor-master-data.fetch-by-id",
            description = "Get sensor by id.",
            tags = {Tags.SENSOR_MASTER_DATA}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The sensor was successfully retrieved.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = SensorDataDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The request is invalid."
                    )
            }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SensorDataDto> find(@Parameter(description = "The id of the sensor.") String id) {
        var sensorData = sensorService.findById(id);
        return ResponseEntity.ok(modelMapper.map(sensorData, SensorDataDto.class));
    }

    /**
     * Delete sensor by id.
     */
    @Operation(
            operationId = "sensor-master-data.delete-by-id",
            description = "Delete sensor by id.",
            tags = {Tags.SENSOR_MASTER_DATA}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The sensor was successfully deleted."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The request is invalid."
                    )
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "The id of the sensor.") String id) {
        sensorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
