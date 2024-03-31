package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.Response;
import de.app.fivegla.business.ThirdPartyApiConfigurationService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.CreateThirdPartyApiConfigurationRequest;
import de.app.fivegla.controller.dto.response.FindAllThirdPartyApiConfigurationsResponse;
import de.app.fivegla.controller.dto.response.inner.ThirdPartyApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ThirdPartyApiConfigurationService thirdPartyApiConfigurationService;

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
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> createThirdPartyApiConfiguration(@Valid @RequestBody CreateThirdPartyApiConfigurationRequest request, Principal principal) {
        log.info("Creating third-party API configuration.");
        var thirdPartyApiConfiguration = request.toEntity();
        thirdPartyApiConfiguration.setTenantId(principal.getName());
        thirdPartyApiConfigurationService.createThirdPartyApiConfiguration(thirdPartyApiConfiguration);
        return ResponseEntity.ok().body(new Response());
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
                    schema = @Schema(implementation = FindAllThirdPartyApiConfigurationsResponse.class)
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
    @GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getThirdPartyApiConfiguration(Principal principal) {
        log.info("Getting third-party API configuration.");
        var thirdPartyApiConfigurations = thirdPartyApiConfigurationService.getThirdPartyApiConfigurations(principal.getName()).stream().map(thirdPartyApiConfiguration -> ThirdPartyApiConfiguration.builder()
                .tenantId(thirdPartyApiConfiguration.getTenantId())
                .manufacturer(thirdPartyApiConfiguration.getManufacturer())
                .enabled(thirdPartyApiConfiguration.isEnabled()).build()).toList();
        return ResponseEntity.ok(FindAllThirdPartyApiConfigurationsResponse.builder()
                .thirdPartyApiConfigurations(thirdPartyApiConfigurations)
                .build());
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param principal    The principal object representing the user.
     * @param manufacturer The manufacturer of the third-party API configuration to delete.
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
    @DeleteMapping(value = "/{manufacturer}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> deleteThirdPartyApiConfiguration(Principal principal, @PathVariable String manufacturer) {
        log.info("Deleting third-party API configuration.");
        thirdPartyApiConfigurationService.deleteThirdPartyApiConfiguration(principal.getName(), Manufacturer.valueOf(manufacturer));
        return ResponseEntity.ok().body(new Response());
    }

}
