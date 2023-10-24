package de.app.fivegla.controller;

import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.api.swagger.OperationTags;
import de.app.fivegla.controller.dto.request.DeviceRegistrationRequest;
import de.app.fivegla.integration.generic.GenericDeviceIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller that handles the registration of devices in the system.
 * It provides RESTful endpoints for device registration.
 */
@RestController
@RequestMapping(BaseMappings.DEVICE)
public class DeviceController {

    private final GenericDeviceIntegrationService genericDeviceIntegrationService;

    public DeviceController(GenericDeviceIntegrationService genericDeviceIntegrationService) {
        this.genericDeviceIntegrationService = genericDeviceIntegrationService;
    }

    /**
     * Registers a device in the system.
     *
     * @param request The device registration request containing the manufacturer,
     *                ID, latitude, and longitude of the device.
     * @return A ResponseEntity indicating the success of the device registration.
     * The response status code will be 201 (Created) if the device has been
     * registered successfully, or 400 (Bad Request) if the request is invalid.
     */
    @Operation(
            operationId = "device.create",
            description = "Registers a device in the system.",
            tags = OperationTags.DEVICE
    )
    @ApiResponse(
            responseCode = "201",
            description = "The device has been registered successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody DeviceRegistrationRequest request) {
        genericDeviceIntegrationService.persist(request.getManufacturer(), request.getId(), request.getLatitude(), request.getLongitude());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes a device from the system.
     *
     * @param id The ID of the device to be deleted.
     * @return A ResponseEntity indicating the success of the device deletion.
     * The response status code will be 204 (No Content) if the device has been
     * deleted successfully, or 400 (Bad Request) if the request is invalid.
     */
    @Operation(
            operationId = "device.delete",
            description = "Deletes a device from the system.",
            tags = OperationTags.DEVICE
    )
    @ApiResponse(
            responseCode = "204",
            description = "The device has been deleted successfully."
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid."
    )
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String id) {
        genericDeviceIntegrationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
