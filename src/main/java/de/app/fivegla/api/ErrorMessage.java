package de.app.fivegla.api;

import lombok.Builder;
import lombok.Getter;

/**
 * Error message.
 */
@Getter
@Builder
public class ErrorMessage {

    /**
     * The error code.
     */
    private Error error;

    /**
     * The error message.
     */
    private String message;

    public String asDetail() {
        return String.format("%s", message);
    }
}
