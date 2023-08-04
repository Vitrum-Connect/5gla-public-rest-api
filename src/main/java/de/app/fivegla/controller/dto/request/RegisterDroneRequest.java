package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for registering a drone.
 */
@Getter
@Setter
@Schema(description = "Request for registering a drone.")
public class RegisterDroneRequest {

    /**
     * The id of the drone.
     */
    @Schema(description = "The id of the drone.")
    private String droneId;
}
