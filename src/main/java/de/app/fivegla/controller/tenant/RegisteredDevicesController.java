package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.RegisteredDevicesService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.RegisterDeviceRequest;
import de.app.fivegla.controller.dto.response.RegisterDeviceResponse;
import de.app.fivegla.persistence.entity.RegisteredDevice;
import io.swagger.v3.oas.annotations.Operation;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.REGISTERED_DEVICES)
public class RegisteredDevicesController implements TenantCredentialApiAccess {

    private final RegisteredDevicesService registeredDevicesService;

    @Operation(
            operationId = "registered-devices.register",
            description = "Registers a device.",
            tags = BaseMappings.REGISTERED_DEVICES
    )
    @ApiResponse(
            responseCode = "201",
            description = "The device was registered.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = RegisterDeviceResponse.class)
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
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> register(@Valid @RequestBody RegisterDeviceRequest request) {
        var registeredDevice = new RegisteredDevice();
        registeredDevice.setName(request.getName());
        registeredDevice.setDescription(request.getDescription());
        registeredDevice.setLongitude(request.getLongitude());
        registeredDevice.setLatitude(request.getLatitude());
        var registeredAndSavedDevice = registeredDevicesService.registerDevice(registeredDevice, request.getGroupId());
        return ResponseEntity.ok(RegisterDeviceResponse.builder()
                .registeredDevice(de.app.fivegla.controller.dto.response.inner.RegisteredDevice.builder()
                        .oid(registeredAndSavedDevice.getOid())
                        .name(registeredAndSavedDevice.getName())
                        .description(registeredAndSavedDevice.getDescription())
                        .longitude(registeredAndSavedDevice.getLongitude())
                        .latitude(registeredAndSavedDevice.getLatitude())
                        .groupId(registeredAndSavedDevice.getGroup().getOid())
                        .build())
                .build());
    }

}
