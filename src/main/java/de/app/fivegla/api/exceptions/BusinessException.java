package de.app.fivegla.api.exceptions;

import de.app.fivegla.api.ErrorMessage;
import lombok.Getter;

/**
 * Exception for business errors.
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * The error message.
     */
    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
