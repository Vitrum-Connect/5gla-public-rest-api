package de.app.fivegla.controller.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Request to register a device.")
public class RegisterDeviceRequest {

    @NotBlank
    @Schema(description = "The name.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "The description.")
    private String description;

    @Schema(description = "The longitude.", requiredMode = Schema.RequiredMode.REQUIRED)
    private double longitude;

    @Schema(description = "The latitude.", requiredMode = Schema.RequiredMode.REQUIRED)
    private double latitude;

    @Schema(description = "The id of the group, if not set, the default group will be used.")
    private String groupId;
}
