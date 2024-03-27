package de.app.fivegla.controller;

import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.CreateTenantRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
public class TenantController {

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
    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateTenantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
