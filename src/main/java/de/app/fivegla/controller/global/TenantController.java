package de.app.fivegla.controller.global;

import de.app.fivegla.api.Format;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.ApiKeyApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.CreateTenantRequest;
import de.app.fivegla.controller.dto.response.CreateTenantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The TenantController class manages the creation of tenants.
 */
@RestController
@RequestMapping(BaseMappings.TENANT)
@RequiredArgsConstructor
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
            description = "Creates a new tenant based on the provided request."
    )
    @ApiResponse(
            responseCode = "201",
            description = "The tenant was created successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateTenantResponse> create(@Valid @RequestBody CreateTenantRequest request) {
        var tenantAndAccessToken = tenantService.create(request.getTenantId(), request.getName(), request.getDescription());
        var tenant = tenantAndAccessToken.tenant();
        var accessToken = tenantAndAccessToken.accessToken();
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateTenantResponse.builder()
                .createdAt(Format.format(tenant.getCreatedAt()))
                .name(tenant.getName())
                .uuid(tenant.getUuid())
                .accessToken(accessToken)
                .build());
    }

}
