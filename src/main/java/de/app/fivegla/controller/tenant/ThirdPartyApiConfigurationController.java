package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.CreateThirdPartyApiConfigurationRequest;
import de.app.fivegla.controller.dto.response.CreateThirdPartyApiConfigurationResponse;
import de.app.fivegla.controller.dto.response.ReadThirdPartyApiConfigurationsResponse;
import de.app.fivegla.controller.dto.response.inner.ThirdPartyApiConfiguration;
import de.app.fivegla.event.events.DataImportEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * The JobController class handles requests related to job operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.THIRD_PARTY_API_CONFIGURATION)
public class ThirdPartyApiConfigurationController implements TenantCredentialApiAccess {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;
    private final TenantService tenantService;

    /**
     * Creates a third-party API configuration and adds it to the system.
     *
     * @param request The request object containing the third-party API configuration details.
     * @return A ResponseEntity with no body and an HTTP status code of 200 (OK) if the configuration is created successfully.
     */
    @Operation(
            summary = "Creates a third-party API configuration.",
            description = "Creates a third-party API configuration and adds it to the system.",
            tags = BaseMappings.THIRD_PARTY_API_CONFIGURATION
    )
    @ApiResponse(
            responseCode = "201",
            description = "The third-party API configuration was created successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateThirdPartyApiConfigurationResponse.class)
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
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> createThirdPartyApiConfiguration(@Valid @RequestBody CreateThirdPartyApiConfigurationRequest request, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var thirdPartyApiConfiguration = request.toEntity();
        thirdPartyApiConfiguration.setTenant(tenant);
        var thirdPartyApiConfigurationCreated = thirdPartyApiConfigurationService.createThirdPartyApiConfiguration(thirdPartyApiConfiguration);
        applicationEventPublisher.publishEvent(new DataImportEvent(thirdPartyApiConfigurationCreated.getId()));
        var response = CreateThirdPartyApiConfigurationResponse.builder()
                .thirdPartyApiConfiguration(ThirdPartyApiConfiguration.builder()
                        .tenantId(thirdPartyApiConfigurationCreated.getTenant().getTenantId())
                        .manufacturer(thirdPartyApiConfigurationCreated.getManufacturer())
                        .fiwarePrefix(thirdPartyApiConfigurationCreated.getFiwarePrefix())
                        .enabled(thirdPartyApiConfigurationCreated.isEnabled())
                        .url(thirdPartyApiConfigurationCreated.getUrl())
                        .uuid(thirdPartyApiConfigurationCreated.getUuid())
                        .lastRun(thirdPartyApiConfigurationCreated.getLastRun().toInstant())
                        .build())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Gets third-party API configuration.
     *
     * @param principal The principal object representing the user.
     * @return A ResponseEntity object containing the third-party API configurations.
     */
    @Operation(
            summary = "Gets third-party API configuration.",
            description = "Gets third-party API configuration.",
            tags = BaseMappings.THIRD_PARTY_API_CONFIGURATION
    )
    @ApiResponse(
            responseCode = "200",
            description = "The third party API configurations were retrieved successfully. The response contains a list of third-party API configurations.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReadThirdPartyApiConfigurationsResponse.class)
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
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getThirdPartyApiConfigurations(Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        return ResponseEntity.ok(ReadThirdPartyApiConfigurationsResponse.builder()
                .thirdPartyApiConfigurations(thirdPartyApiConfigurationService.getThirdPartyApiConfigurations(tenant.getTenantId())
                        .stream()
                        .map(thirdPartyApiConfiguration -> ThirdPartyApiConfiguration.builder()
                                .tenantId(thirdPartyApiConfiguration.getTenant().getTenantId())
                                .manufacturer(thirdPartyApiConfiguration.getManufacturer())
                                .fiwarePrefix(thirdPartyApiConfiguration.getFiwarePrefix())
                                .enabled(thirdPartyApiConfiguration.isEnabled())
                                .url(thirdPartyApiConfiguration.getUrl())
                                .uuid(thirdPartyApiConfiguration.getUuid())
                                .lastRun(thirdPartyApiConfiguration.getLastRun().toInstant())
                                .build()).toList())
                .build());
    }

    /**
     * Gets all third-party API configuration.
     *
     * @param principal The principal object representing the user.
     * @return A ResponseEntity object containing the third-party API configurations.
     */
    @Operation(
            summary = "Gets third-party API configuration.",
            description = "Gets third-party API configuration.",
            tags = BaseMappings.THIRD_PARTY_API_CONFIGURATION
    )
    @ApiResponse(
            responseCode = "200",
            description = "The third party API configurations were retrieved successfully. The response contains a list of third-party API configurations.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReadThirdPartyApiConfigurationsResponse.class)
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
    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getThirdPartyApiConfiguration(@PathVariable(value = "uuid") String uuid, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        return ResponseEntity.ok(ReadThirdPartyApiConfigurationsResponse.builder()
                .thirdPartyApiConfigurations(thirdPartyApiConfigurationService.getThirdPartyApiConfigurations(tenant.getTenantId(), uuid)
                        .stream()
                        .map(thirdPartyApiConfiguration -> ThirdPartyApiConfiguration.builder()
                                .tenantId(thirdPartyApiConfiguration.getTenant().getTenantId())
                                .manufacturer(thirdPartyApiConfiguration.getManufacturer())
                                .fiwarePrefix(thirdPartyApiConfiguration.getFiwarePrefix())
                                .enabled(thirdPartyApiConfiguration.isEnabled())
                                .url(thirdPartyApiConfiguration.getUrl())
                                .uuid(thirdPartyApiConfiguration.getUuid())
                                .lastRun(thirdPartyApiConfiguration.getLastRun().toInstant())
                                .build()).toList())
                .build());
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param principal The principal object representing the user.
     * @return A ResponseEntity object with no body and an HTTP status code of 200 (OK) if the configuration is deleted successfully.
     */
    @Operation(
            summary = "Deletes a third-party API configuration.",
            description = "Deletes a third-party API configuration.",
            tags = BaseMappings.THIRD_PARTY_API_CONFIGURATION
    )
    @ApiResponse(
            responseCode = "200",
            description = "The third-party API configuration was deleted successfully.",
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
    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> deleteThirdPartyApiConfiguration(@PathVariable(value = "uuid") String uuid, Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        thirdPartyApiConfigurationService.deleteThirdPartyApiConfiguration(tenant.getTenantId(), uuid);
        return ResponseEntity.ok().body(new Response());
    }

}
