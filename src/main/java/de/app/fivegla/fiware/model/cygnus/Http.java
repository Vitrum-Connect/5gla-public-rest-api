package de.app.fivegla.fiware.model.cygnus;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import lombok.*;

/**
 * Represents an HTTP object that contains a URL.
 * This class implements the Validatable interface.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Http implements Validatable {

    /**
     * The URL.
     */
    private String url;

    @Override
    public void validate() {
        // NOP¶
    }
}
