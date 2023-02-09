package de.app.fivegla.api.exceptions;

import de.app.fivegla.api.ErrorMessage;
import lombok.Getter;

/**
 * Exception for business errors.
 */
public class BusinessException extends RuntimeException {

    /**
     * The error message.
     */
    @Getter
    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }
}
