package de.app.fivegla.fiware.model.cygnus;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.cygnus.enums.SubscriptionStatus;
import lombok.*;

import java.time.Instant;


/**
 * Represents a subscription for receiving notifications.
 * Implements the Validatable interface to provide validation functionality.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscription implements Validatable {

    /**
     * The id of the subscription.
     */
    private String id;

    /**
     * The description of the subscription.
     */
    private String description;

    /**
     * The subject of the subscription.
     */
    private Subject subject;

    /**
     * The notification of the subscription.
     */
    private Notification notification;

    /**
     * The expiration date of the subscription.
     */
    private Instant expires;

    /**
     * The status of the subscription.
     */
    private SubscriptionStatus status;

    /**
     * The throttling of the subscription.
     */
    private int throttling;

    @Override
    public void validate() {
        if (subject == null) {
            throw new FiwareIntegrationLayerException("Subject must not be null");
        }
        subject.validate();

        if (notification == null) {
            throw new FiwareIntegrationLayerException("Notification must not be null");
        }
        notification.validate();

        if (status == null) {
            throw new FiwareIntegrationLayerException("Status must not be null");
        }
        if (throttling < 0) {
            throw new FiwareIntegrationLayerException("Throttling must not be negative");
        }
    }
}
