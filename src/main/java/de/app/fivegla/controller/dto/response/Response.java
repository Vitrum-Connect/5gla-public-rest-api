package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Format;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.Instant;

/**
 * Base class for all responses.
 */
@Getter
abstract class Response {

    /**
     * The timestamp of the response.
     */
    @Schema(description = "The timestamp of the response.")
    private final String timestamp;

    public Response() {
        timestamp = Format.format(Instant.now());
    }
}
