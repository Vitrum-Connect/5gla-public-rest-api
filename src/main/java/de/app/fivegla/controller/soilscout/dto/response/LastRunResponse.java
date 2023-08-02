package de.app.fivegla.controller.soilscout.dto.response;

import de.app.fivegla.api.Manufacturer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

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
    @Schema(description = "The last run for each manufacturer.")
    private final Map<Manufacturer, String> lastRuns;

}
