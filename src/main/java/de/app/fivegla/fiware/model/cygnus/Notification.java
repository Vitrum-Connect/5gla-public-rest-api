package de.app.fivegla.fiware.model.cygnus;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.cygnus.enums.AttrsFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * A class representing a notification.
 * It implements the Validatable interface to provide validation functionality.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification implements Validatable {

    /**
     * The attributes.
     */
    private List<String> attrs;

    /**
     * The except attributes.
     */
    private List<String> exceptAttrs;

    /**
     * The HTTP object.
     */
    private Http http;

    /**
     * The custom HTTP object.
     */
    private HttpCustom httpCustom;

    /**
     * The attribute format.
     */
    private AttrsFormat attrsFormat;

    /**
     * The of the last notification sent.
     */
    private Date timesSent;

    /**
     * The of the last notification received.
     */
    private Date lastNotification;

    /**
     * The of the last failure.
     */
    private Date lastFailure;

    /**
     * The reason of the last failure.
     */
    private String lastFailureReason;

    /**
     * The number of failures.
     */
    private int failsCounter;

    /**
     * The of the last success.
     */
    private Date lastSuccess;

    /**
     * The code of the last success.
     */
    private String lastSuccessCode;

    /**
     * Only send changed attributes.
     */
    private boolean onlyChangedAttrs;

    /**
     * Covered.
     */
    private boolean covered;

    @Override
    public void validate() {
        if (attrs != null && exceptAttrs != null) {
            throw new RuntimeException("Either attrs or exceptAttrs must be set");
        }
        if (http == null && httpCustom == null) {
            throw new RuntimeException("Either http or httpCustom must be set");
        }
        if (http != null && httpCustom != null) {
            throw new RuntimeException("Either http or httpCustom must be set");
        }
        if (http != null) {
            http.validate();
        }
        if (httpCustom != null) {
            httpCustom.validate();
        }
    }
}
