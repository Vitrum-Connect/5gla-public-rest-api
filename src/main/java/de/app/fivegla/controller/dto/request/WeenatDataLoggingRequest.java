package de.app.fivegla.controller.dto.request;

import de.app.fivegla.integration.weenat.model.Measurement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Request for data logging.
 */
@Getter
@Setter
@Schema(description = "Request for data logging.")
public class WeenatDataLoggingRequest {

    /**
     * The measurements to log.
     */
    @Schema(description = "The measurements to log.")
    private List<Measurement> measurements;

}
