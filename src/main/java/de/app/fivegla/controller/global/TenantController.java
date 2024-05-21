package de.app.fivegla.controller.global;

import de.app.fivegla.api.Format;
import de.app.fivegla.api.Response;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.ApiKeyApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.CreateTenantRequest;
import de.app.fivegla.controller.dto.request.UpdateTenantRequest;
import de.app.fivegla.controller.dto.response.CreateTenantResponse;
import de.app.fivegla.controller.dto.response.ReadTenantsResponse;
import de.app.fivegla.controller.dto.response.UpdateTenantResponse;
import de.app.fivegla.controller.dto.response.inner.Tenant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The TenantController class manages the creation of tenants.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.TENANT)
public class TenantController implements ApiKeyApiAccess {

    private final TenantService tenantService;

    /**
     * Creates a new tenant based on the provided request.
     *
     * @param request The {@code CreateTenantRequest} object representing the request for tenant creation.
     * @return A {@code RequestEntity<Void>} object representing the HTTP response containing information about the created tenant.
     */
    @Operation(
            operationId = "tenant.create",
            description = "Creates a new tenant based on the provided request.",
            tags = BaseMappings.TENANT
    )
    @ApiResponse(
            responseCode = "201",
            description = "The tenant was created successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateTenantResponse.class)
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
    public ResponseEntity<? extends Response> create(@Valid @RequestBody CreateTenantRequest request) {
        var tenantAndAccessToken = tenantService.create(request.getTenantId(), request.getName(), request.getDescription());
        var tenant = tenantAndAccessToken.tenant();
        var accessToken = tenantAndAccessToken.accessToken();
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateTenantResponse.builder()
                .createdAt(Format.format(tenant.getCreatedAt()))
                .name(tenant.getName())
                .tenantId(tenant.getTenantId())
                .accessToken(accessToken)
                .build());
    }

    @Operation(
            operationId = "tenant.update",
            description = "Update a new tenant based on the provided request.",
            tags = BaseMappings.TENANT
    )
    @ApiResponse(
            responseCode = "201",
            description = "The tenant was updated successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateTenantResponse.class)
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
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> update(@Valid @RequestBody UpdateTenantRequest request) {
        var tenant = tenantService.update(request.getTenantId(), request.getName(), request.getDescription());
        return ResponseEntity.ok(UpdateTenantResponse.builder()
                .updatedAt(Format.format(tenant.getCreatedAt()))
                .name(tenant.getName())
                .uuid(tenant.getTenantId())
                .build());
    }

    /**
     * Finds all tenants.
     *
     * @return A {@code ResponseEntity<FindAllTenantsResponse>} object representing the HTTP response containing a list of tenants.
     */
    @Operation(
            operationId = "tenant.findAll",
            description = "Finds all tenants.",
            tags = BaseMappings.TENANT
    )
    @ApiResponse(
            responseCode = "200",
            description = "The tenants are found successfully. The response contains a list of tenants.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ReadTenantsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> findAll() {
        var tenants = tenantService.findAll().stream().map(tenant -> Tenant.builder()
                .createdAt(Format.format(tenant.getCreatedAt()))
                .name(tenant.getName())
                .tenantId(tenant.getTenantId())
                .description(tenant.getDescription())
                .build()).toList();
        return ResponseEntity.ok(ReadTenantsResponse.builder().tenants(tenants).build());
    }

    @Operation(
            operationId = "tenant.delete",
            description = "Delete a tenant based on the provided request.",
            tags = BaseMappings.TENANT
    )
    @ApiResponse(
            responseCode = "204",
            description = "The tenant was deleted successfully.",
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
    @DeleteMapping(value = "/{tenantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> delete(@PathVariable String tenantId) {
        tenantService.delete(tenantId);
        return ResponseEntity.noContent().build();
    }

}
