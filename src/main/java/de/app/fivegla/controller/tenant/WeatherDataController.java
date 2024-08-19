package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.ManualWeatherDataService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.PrecipitationRequest;
import de.app.fivegla.controller.dto.request.WeatherDataRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.WEATHER_DATA)
public class WeatherDataController implements TenantCredentialApiAccess {

    private final TenantService tenantService;
    private final ManualWeatherDataService manualWeatherDataService;

    @Operation(
            operationId = "weather-data.add-manual",
            description = "Add a manual precipitation measurement.",
            tags = BaseMappings.WEATHER_DATA
    )
    @ApiResponse(
            responseCode = "200",
            description = "The precipitation measurement was added.",
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
    @PostMapping(value = "/precipitation/manual", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> addManualPrecipitation(@Parameter(description = "The request to add a precipitation measurement.") @Valid @RequestBody PrecipitationRequest request, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        manualWeatherDataService.addPrecipitationEvent(tenant, request);
        return ResponseEntity.ok(new Response());
    }

    @Operation(
            operationId = "weather-data.add-manual",
            description = "Add a manual weather data measurement.",
            tags = BaseMappings.WEATHER_DATA
    )
    @ApiResponse(
            responseCode = "200",
            description = "The weather data measurement was added.",
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
    @PostMapping(value = "/weather-data/manual", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> addManualWeatherData(@Parameter(description = "The request to add a weather data measurement.") @Valid @RequestBody WeatherDataRequest request, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        manualWeatherDataService.addManualWeatherData(tenant, request);
        return ResponseEntity.ok(new Response());
    }

}
