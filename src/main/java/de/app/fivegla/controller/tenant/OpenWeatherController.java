package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.integration.openweather.OpenWeatherIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.OPEN_WEATHER)
public class OpenWeatherController implements TenantCredentialApiAccess {

    private final OpenWeatherIntegrationService openWeatherIntegrationService;
    private final TenantService tenantService;

    @Operation(
            operationId = "open-weather.import",
            description = "Imports weather data from OpenWeather.",
            tags = BaseMappings.OPEN_WEATHER
    )
    @ApiResponse(
            responseCode = "200",
            description = "The weather data was imported.",
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
    @PostMapping(value = "/import/{sensorId}/{startDateInThePast}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> importOpenWeatherDataFromThePast(@PathVariable @Schema(description = "The sensor ID.") String sensorId,
                                                                               @PathVariable(value = "startDateInThePast") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDateInThePast,
                                                                               Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        log.info("Importing weather data from OpenWeather for sensor '{}'.", sensorId);
        openWeatherIntegrationService.importWeatherDataFromThePast(tenant, sensorId, startDateInThePast);
        return ResponseEntity.ok().build();
    }

}
