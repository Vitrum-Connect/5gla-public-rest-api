package de.app.fivegla.controller;

import de.app.fivegla.api.Tags;
import de.app.fivegla.controller.dto.SensorDataDTO;
import de.app.fivegla.controller.dto.request.CreateSensorDataRequest;
import de.app.fivegla.controller.dto.request.UpdateSensorDataRequest;
import de.app.fivegla.controller.dto.response.SensorDataResponse;
import de.app.fivegla.domain.GeoLocation;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Parameter(description = "The request to create a sensor.") @Valid @RequestBody CreateSensorDataRequest createSensorDataRequest) {
        var sensorData = SensorMasterData.builder()
                .sensorId(createSensorDataRequest.getSensorId())
                .sensorName(createSensorDataRequest.getSensorName())
                .sensorType(createSensorDataRequest.getSensorType())
                .geoLocation(GeoLocation.builder()
                        .latitude(createSensorDataRequest.getGeoLocation().getLatitude())
                        .longitude(createSensorDataRequest.getGeoLocation().getLongitude()).build())
                .build();
        sensorService.createSensor(sensorData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Update an existing sensor.
     */
    @Operation(
            operationId = "sensor-master-data.update",
            description = "Update an existing sensor.",
            tags = {Tags.SENSOR_MASTER_DATA},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The request to update an existing sensor.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateSensorDataRequest.class)
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The sensor was successfully updated."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The request is invalid."
                    )
            }
    )
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@Parameter(description = "The request to update an existing sensor.") @Valid @RequestBody UpdateSensorDataRequest updateSensorDataRequest) {
        var sensorData = SensorMasterData.builder()
                .sensorId(updateSensorDataRequest.getSensorId())
                .sensorName(updateSensorDataRequest.getSensorName())
                .sensorType(updateSensorDataRequest.getSensorType())
                .geoLocation(GeoLocation.builder()
                        .latitude(updateSensorDataRequest.getGeoLocation().getLatitude())
                        .longitude(updateSensorDataRequest.getGeoLocation().getLongitude()).build())
                .build();
        sensorService.updateSensor(sensorData);
        return ResponseEntity.ok().build();
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
                                            implementation = SensorDataDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The request is invalid."
                    )
            }
    )
    @GetMapping(value = {"/", "/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SensorDataResponse> find(@Parameter(description = "The id of the sensor.", required = true) @PathVariable(required = false) Optional<String> id) {
        final List<SensorMasterData> sensorData;
        sensorData = id.map(s -> Collections.singletonList(sensorService.findById(s))).orElseGet(sensorService::findAll);
        var sensorDataDtos = sensorData.stream().map(s -> modelMapper.map(s, SensorDataDTO.class)).toList();
        return ResponseEntity.ok(SensorDataResponse.builder().sensorData(sensorDataDtos).build());
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
    public ResponseEntity<Void> delete(@Parameter(description = "The id of the sensor.", required = true) @PathVariable String id) {
        sensorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
