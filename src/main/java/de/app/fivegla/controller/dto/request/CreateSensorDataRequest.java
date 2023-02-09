package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for creating sensor data.
 */
@Getter
@Setter
@Schema(description = "Request for creating sensor data.")
public class CreateSensorDataRequest {

    /**
     * The sensor id.
     */
    @Schema(description = "The sensor id.")
    @NotBlank
    private String sensorId;

}
