package de.app.fivegla.controller.dto.request;

import de.app.fivegla.integration.agvolution.model.SeriesEntry;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Request for data logging.
 */
@Setter
@Getter
@Schema
public class AgvolutionDataLoggingRequest {
    /**
     * Represents a series entry in a schema.
     */
    @Schema(description = "Represents a series entry in a schema.")
    private SeriesEntry seriesEntry;
}
