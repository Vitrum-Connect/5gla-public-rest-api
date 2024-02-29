package de.app.fivegla.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This class represents the subscription status of a service or application.
 * It provides methods to get and set the status of subscriptions.
 */
@Setter
@Component
@Scope("singleton")
public class SubscriptionStatus {

    @Getter
    @Value("${app.fiware.subscriptions.enabled}")
    private boolean subscriptionsEnabled;

    private boolean subscriptionsSent;

    /**
     * Checks if subscriptions are enabled and if subscriptions have not been sent yet.
     *
     * @return true if subscriptions are enabled and subscriptions have not been sent yet, false otherwise
     */
    public boolean sendOutSubscriptions() {
        return subscriptionsEnabled && !subscriptionsSent;
    }

}
