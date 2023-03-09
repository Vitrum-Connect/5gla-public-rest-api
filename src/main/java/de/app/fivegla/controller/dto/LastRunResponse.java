package de.app.fivegla.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Response wrapper.
 */
@Builder
@Schema(description = "Response wrapper.")
public class LastRunResponse extends Response {

    /**
     * The last run.
     */
    @Getter
    @Schema(description = "The last run.")
    private final String lastRun;

}
