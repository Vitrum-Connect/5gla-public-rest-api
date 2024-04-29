package de.app.fivegla.fiware.model.cygnus;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * A class representing a custom HTTP request.
 * This class provides the necessary properties to build and send an HTTP request. It also implements the Validatable interface
 * to allow for validation of the request parameters.
 *
 * @see Validatable
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpCustom implements Validatable {

    /**
     * The URL.
     */
    private String url;

    /**
     * The headers.
     */
    private Map<String, String> headers;

    /**
     * The query string.
     */
    private Map<String, String> qs;

    /**
     * The method.
     */
    private String method;

    /**
     * The payload.
     */
    private String payload;

    @Override
    public void validate() {
        // NOP
    }
}
