package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Request to add a device position.")
public class AddDevicePositionRequest {

    @NotBlank
    @Schema(description = "The transaction id.")
    private String transactionId;

    @NotBlank
    @Schema(description = "The device id.")
    private String deviceId;

    @Schema(description = "The latitude of the device.")
    private double latitude;

    @Schema(description = "The longitude of the device.")
    private double longitude;
}
