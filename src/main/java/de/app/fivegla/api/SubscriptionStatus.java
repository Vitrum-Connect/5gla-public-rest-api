package de.app.fivegla.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * This class represents the subscription status of a service or application.
 * It provides methods to get and set the status of subscriptions.
 */
@Component
@Scope("singleton")
public class SubscriptionStatus {

    @Getter
    @Value("${app.fiware.subscriptions.enabled}")
    private boolean subscriptionsEnabled;

    private final HashMap<String, Boolean> subscriptionsSent = new HashMap<>();

    /**
     * Checks if subscriptions are enabled and if subscriptions have not been sent yet.
     *
     * @return true if subscriptions are enabled and subscriptions have not been sent yet, false otherwise
     */
    public boolean sendOutSubscriptions(String tenantId) {
        return subscriptionsEnabled && subscriptionsSent.get(tenantId) != null && !subscriptionsSent.get(tenantId);
    }

    /**
     * Sets the status of subscriptions for a specific tenant.
     *
     * @param tenantId The ID of the tenant for which the status of subscriptions is being set.
     */
    public void subscriptionSent(String tenantId) {
        subscriptionsSent.put(tenantId, true);

    }

}
