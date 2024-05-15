package de.app.fivegla.api;

import de.app.fivegla.business.TenantService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * This class represents the subscription status of a service or application.
 * It provides methods to get and set the status of subscriptions.
 */
@Slf4j
@Component
@Scope("singleton")
@RequiredArgsConstructor
public class SubscriptionStatus {

    private final TenantService tenantService;

    @Getter
    @Value("${app.fiware.subscriptions.enabled}")
    private boolean subscriptionsEnabled;

    private final HashMap<String, Boolean> subscriptionsSent = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("Initializing subscriptions sent status for all tenants.");
        var tenants = tenantService.findAll();
        if (null == tenants) {
            log.warn("Currently there are no tenants within the system.");
        } else {
            tenants.forEach(tenant -> subscriptionsSent.put(tenant.getTenantId(), false));
        }
    }

    /**
     * Checks if subscriptions are enabled and if subscriptions have not been sent yet.
     *
     * @return true if subscriptions are enabled and subscriptions have not been sent yet, false otherwise
     */
    public boolean sendOutSubscriptions(String tenantId) {
        if (subscriptionsSent.get(tenantId) == null) {
            log.warn("Tenant with ID {} not yet found.", tenantId);
            return false;
        } else {
            return subscriptionsEnabled && !subscriptionsSent.get(tenantId);
        }
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
