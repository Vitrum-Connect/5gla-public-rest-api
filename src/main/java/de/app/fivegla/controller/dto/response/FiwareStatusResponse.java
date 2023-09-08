package de.app.fivegla.controller.dto.response;

import de.app.fivegla.fiware.model.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Response wrapper.
 */
@Getter
@Builder
@Schema(description = "Response wrapper.")
public class FiwareStatusResponse {

    /**
     * The status of the fiware connection.
     */
    @Schema(description = "The status of the fiware connection.")
    private HttpStatus fiwareStatus;

    /**
     * The version of the fiware connection.
     */
    @Schema(description = "The version of the fiware connection.")
    private Version fiwareVersion;
}
