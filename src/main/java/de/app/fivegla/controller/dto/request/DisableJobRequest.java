package de.app.fivegla.controller.dto.request;

import de.app.fivegla.api.Manufacturer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request to disable a job.
 */
@Getter
@Setter
@Schema(description = "Represents a request to disable a job.")
public class DisableJobRequest {

    /**
     * Represents the manufacturer of a sensor or device.
     */
    @Schema(description = "The manufacturer of the sensor or device.")
    private Manufacturer manufacturer;

}
