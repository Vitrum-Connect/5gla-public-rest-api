package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import de.app.fivegla.integration.fiware.model.Version;
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
public class FiwareStatusResponse extends Response {

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
