package de.app.fivegla.controller.dto.request;

import de.app.fivegla.integration.sentek.model.csv.Reading;
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
public class SentekDataLoggingRequest {

    /**
     * Represents a list of readings to log.
     */
    @Schema(description = "The readings to log.")
    private List<Reading> readings;

}
