package de.app.fivegla.controller.micasense.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Response wrapper.
 */
@Getter
@Builder
@Schema(description = "Response wrapper.")
public class LastRunResponse extends Response {

    /**
     * The last run.
     */
    @Schema(description = "The last run.")
    private final String lastRun;

}
