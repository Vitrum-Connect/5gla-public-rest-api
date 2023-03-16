package de.app.fivegla.controller.dto;

import de.app.fivegla.integration.soilscout.model.Sensor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Response wrapper.
 */
@Builder
@Schema(description = "Response wrapper.")
public class SoilScoutSensorResponse extends Response {

    /**
     * The sensors.
     */
    @Getter
    @Schema(description = "The sensors.")
    private List<Sensor> sensors;
}
