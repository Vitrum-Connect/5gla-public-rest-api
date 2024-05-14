package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.DevicePositionService;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.AddDevicePositionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.DEVICE_POSITION)
public class DevicePositionController implements TenantCredentialApiAccess {

    private final DevicePositionService devicePositionService;
    private final TenantService tenantService;
    private final GroupService groupService;

    /**
     * Adds a device position.
     *
     * @param deviceId      The device ID.
     * @param transactionId The transaction ID.
     * @param request       The request.
     * @param principal     The principal.
     * @return The response.
     */
    @Operation(
            operationId = "device-position.add",
            description = "Adds a device position.",
            tags = BaseMappings.DEVICE_POSITION
    )
    @ApiResponse(
            responseCode = "201",
            description = "The device position was added successfully.",
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
    @PostMapping(value = "/{deviceId}/{transactionId}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> addDevicePosition(@PathVariable @Parameter(description = "The device ID") String deviceId,
                                                                @PathVariable @Parameter(description = "The transaction ID") String transactionId,
                                                                @Valid @RequestBody AddDevicePositionRequest request,
                                                                Principal principal) {
        var tenant = validateTenant(tenantService, principal);
        var group = groupService.getOrDefault(tenant, request.getGroupId());
        devicePositionService.createDevicePosition(
                tenant,
                group,
                EntityType.DEVICE_POSITION,
                deviceId,
                transactionId,
                request.getLatitude(),
                request.getLongitude());
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response());
    }

}
