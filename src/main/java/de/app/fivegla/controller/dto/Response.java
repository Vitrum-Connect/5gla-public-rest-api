package de.app.fivegla.controller.dto;

import lombok.Getter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Base class for all responses.
 */
abstract class Response {

    @Getter
    private final String timestamp;

    public Response() {
        timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }
}
