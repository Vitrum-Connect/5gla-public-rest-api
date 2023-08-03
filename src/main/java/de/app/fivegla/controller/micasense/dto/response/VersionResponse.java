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
public class VersionResponse extends Response {

    /**
     * The version.
     */
    @Schema(description = "The version.")
    private String version;

}
