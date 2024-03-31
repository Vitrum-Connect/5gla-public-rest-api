package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Response;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.AgvolutionDataLoggingRequest;
import de.app.fivegla.controller.dto.request.SentekDataLoggingRequest;
import de.app.fivegla.controller.dto.request.WeenatDataLoggingRequest;
import de.app.fivegla.integration.agvolution.AgvolutionFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.agvolution.AgvolutionSensorIntegrationService;
import de.app.fivegla.integration.sentek.SentekFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.sentek.SentekSensorIntegrationService;
import de.app.fivegla.integration.weenat.WeenatFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.weenat.WeenatPlotIntegrationService;
import de.app.fivegla.integration.weenat.model.Measurements;
import de.app.fivegla.persistence.ApplicationDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The DataLoggingController class handles the logging of Sentek data for a specific sensor.
 */
@Slf4j
@RestController
@RequestMapping(BaseMappings.DEVICE_MEASUREMENT)
@RequiredArgsConstructor
public class DeviceMeasurementController implements TenantCredentialApiAccess {

    private final SentekSensorIntegrationService sentekSensorIntegrationService;
    private final SentekFiwareIntegrationServiceWrapper sentekFiwareIntegrationServiceWrapper;
    private final WeenatPlotIntegrationService weenatPlotIntegrationService;
    private final WeenatFiwareIntegrationServiceWrapper weenatFiwareIntegrationServiceWrapper;
    private final AgvolutionSensorIntegrationService agvolutionSensorIntegrationService;
    private final AgvolutionFiwareIntegrationServiceWrapper agvolutionFiwareIntegrationServiceWrapper;
    private final ApplicationDataRepository applicationDataRepository;

    /**
     * This method logs the Sentek data for a specific sensor.
     *
     * @param sensorId The ID of the sensor
     * @param request  The SentekDataLoggingRequest object containing the data readings
     * @return The ResponseEntity object indicating the success or failure of the data logging
     */
    @Operation(
            operationId = "device-measurement.sentek",
            description = "Logs the Sentek data for a specific sensor.",
            tags = BaseMappings.DEVICE_MEASUREMENT
    )
    @ApiResponse(
            responseCode = "201",
            description = "The Sentek data was logged successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/sentek/{sensorId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sentek(@PathVariable @Parameter(description = "The ID of the sensor.", required = true) int sensorId,
                                         @Parameter(description = "The logging request.", required = true) @RequestBody SentekDataLoggingRequest request, Principal principal) {
        AtomicBoolean dataHasBeenLogged = new AtomicBoolean(false);
        var optionalTenant = applicationDataRepository.getTenant(principal.getName());
        if (optionalTenant.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessage.builder()
                            .error(Error.TENANT_NOT_FOUND)
                            .message("No tenant found for id " + principal.getName())
                            .build()
                            .asDetail());
        } else {
            var tenant = optionalTenant.get();
            sentekSensorIntegrationService.fetchAll().stream().filter(sensor -> sensor.getId() == sensorId).findFirst().ifPresentOrElse(sensor -> {
                log.info("Persisting {} measurements for sensor {}.", request.getReadings().size(), sensorId);
                sentekFiwareIntegrationServiceWrapper.persist(tenant, sensor, request.getReadings());
                dataHasBeenLogged.set(true);
            }, () -> {
                log.warn("No SENTEK sensor found for id {}.", sensorId);
                dataHasBeenLogged.set(false);
            });

            if (dataHasBeenLogged.get()) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
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

    /**
     * This method logs the Weenat data for a specific plot.
     *
     * @param plotId  The ID of the plot
     * @param request The WeenatDataLoggingRequest object containing the data measurements
     * @return The ResponseEntity object indicating the success or failure of the data logging
     */
    @Operation(
            operationId = "device-measurement.weenat",
            description = "Logs the Weenat data for a specific plot.",
            tags = BaseMappings.DEVICE_MEASUREMENT
    )
    @ApiResponse(
            responseCode = "201",
            description = "The Weenat data was logged successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/weenat/{plotId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> weenat(@PathVariable @Parameter(description = "The ID of the plot.", required = true) int plotId,
                                         @RequestBody @Parameter(description = "The logging request.", required = true) WeenatDataLoggingRequest request, Principal principal) {
        AtomicBoolean dataHasBeenLogged = new AtomicBoolean(false);
        var optionalTenant = applicationDataRepository.getTenant(principal.getName());
        if (optionalTenant.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessage.builder()
                            .error(Error.TENANT_NOT_FOUND)
                            .message("No tenant found for id " + principal.getName())
                            .build()
                            .asDetail());
        } else {
            var tenant = optionalTenant.get();
            weenatPlotIntegrationService.fetchAll().stream().filter(plot -> plot.getId() == plotId).findFirst().ifPresentOrElse(plot -> {
                log.info("Persisting {} measurements for plot {}.", request.getMeasurements().size(), plotId);
                weenatFiwareIntegrationServiceWrapper.persist(tenant, plot, Measurements.builder().measurements(request.getMeasurements()).plot(plot).build());
                dataHasBeenLogged.set(true);
            }, () -> {
                log.warn("No WEENAT sensor found for id {}.", plotId);
                dataHasBeenLogged.set(false);
            });

            if (dataHasBeenLogged.get()) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
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

    /**
     * This method logs the Agvolution data for a specific device.
     *
     * @param deviceId The ID of the device
     * @param request  The AgvolutionDataLoggingRequest object containing the data measurements
     * @return The ResponseEntity object indicating the success or failure of the data logging
     */
    @Operation(
            operationId = "device-measurement.agvolution",
            description = "Logs the Agvolution data for a specific device.",
            tags = BaseMappings.DEVICE_MEASUREMENT
    )
    @ApiResponse(
            responseCode = "201",
            description = "The Agvolution data was logged successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/agvolution/{deviceId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> agvolution(@PathVariable @Parameter(description = "The ID of the device.", required = true) String deviceId,
                                             @RequestBody @Parameter(description = "The logging request.", required = true) AgvolutionDataLoggingRequest request, Principal principal) {
        AtomicBoolean dataHasBeenLogged = new AtomicBoolean(false);
        var optionalTenant = applicationDataRepository.getTenant(principal.getName());
        if (optionalTenant.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessage.builder()
                            .error(Error.TENANT_NOT_FOUND)
                            .message("No tenant found for id " + principal.getName())
                            .build()
                            .asDetail());
        } else {
            var tenant = optionalTenant.get();
            agvolutionSensorIntegrationService.fetchAll().stream().filter(device -> device.getId().equals(deviceId)).findFirst().ifPresentOrElse(plot -> {
                log.info("Persisting {} measurements for device {}.", request.getSeriesEntry().getTimeSeriesEntries().size(), deviceId);
                agvolutionFiwareIntegrationServiceWrapper.persist(tenant, request.getSeriesEntry());
                dataHasBeenLogged.set(true);
            }, () -> {
                log.warn("No AGVOLUTION sensor found for id {}.", deviceId);
                dataHasBeenLogged.set(false);
            });

            if (dataHasBeenLogged.get()) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity
                        .badRequest()
                        .body(ErrorMessage.builder()
                                .error(Error.AGVOLUTION_COULD_NOT_FIND_SENSOR_FOR_ID)
                                .message("No device found for id " + deviceId)
                                .build()
                                .asDetail());
            }
        }
    }

}
