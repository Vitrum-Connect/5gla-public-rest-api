package de.app.fivegla.controller.aop;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Custom exception handler.
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handle business exceptions.
     *
     * @param businessException - Business exception
     * @throws ErrorResponseException Error response exception
     */
    @ExceptionHandler(value = {BusinessException.class})
    public ProblemDetail handleBusinessExceptions(BusinessException businessException) throws ErrorResponseException {
        log.error("A business exception occurred: ", businessException);
        var errorMessage = ErrorMessage.builder()
                .error(businessException.getErrorMessage().getError())
                .message(businessException.getErrorMessage().getMessage()).build();
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage.asDetail());
        problemDetail.setTitle(errorMessage.getError().asTitle());
        return problemDetail;
    }

    /**
     * Handle invalid requests.
     *
     * @throws ErrorResponseException Error response exception
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, IllegalArgumentException.class, InvalidFormatException.class, HttpMessageNotReadableException.class})
    public ProblemDetail handleCommonExceptions(Throwable t) throws ErrorResponseException {
        log.error("An invalid request occurred.", t);
        var errorMessage = ErrorMessage.builder()
                .error(Error.INVALID_REQUEST)
                .message("This was an invalid request. Please check the request parameters and try again.").build();
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage.asDetail());
        problemDetail.setTitle(errorMessage.getError().asTitle());
        return problemDetail;
    }

}
