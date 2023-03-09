package de.app.fivegla.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Response wrapper.
 */
@Builder
@Schema(description = "Response wrapper.")
public class VersionResponse extends Response {

    /**
     * The version.
     */
    @Getter
    @Schema(description = "The version.")
    private String version;

}
