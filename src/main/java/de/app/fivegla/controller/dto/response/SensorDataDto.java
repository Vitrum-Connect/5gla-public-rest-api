package de.app.fivegla.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * The sensor data.
 */
@Getter
@Setter
@Schema(description = "The sensor data.")
public class SensorDataDto {

    /**
     * The sensor id.
     */
    @Schema(description = "The sensor id.")
    private String sensorId;

}
