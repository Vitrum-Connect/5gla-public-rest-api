package de.app.fivegla.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Error message.
 */
@Getter
@Builder
@Schema(description = "Error message.")
public class ErrorMessage extends Response {

    /**
     * The error code.
     */
    @Schema(description = "The error code.")
    private Error error;

    /**
     * The error message.
     */
    @Schema(description = "The error message.")
    private String message;

    public String asDetail() {
        return String.format("%s", message);
    }
}
