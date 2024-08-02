package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response for orthophoto triggering.
 */
@Getter
@Setter
@Builder
@Schema(description = "Response for orthophoto triggering.")
public class OrthphotoTriggeringResponse extends Response {

    /**
     * The uuid of the process underneath.
     */
    @Schema(description = "The uuid of the process underneath.")
    private String uuid;

}
