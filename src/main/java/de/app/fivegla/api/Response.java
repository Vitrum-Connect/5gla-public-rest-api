package de.app.fivegla.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.Instant;

/**
 * Base class for all responses.
 */
@Getter
public class Response {

    /**
     * The timestamp of the response.
     */
    @Schema(description = "The timestamp of the response.")
    private final String timestamp;

    public Response() {
        timestamp = Format.format(Instant.now());
    }
}
