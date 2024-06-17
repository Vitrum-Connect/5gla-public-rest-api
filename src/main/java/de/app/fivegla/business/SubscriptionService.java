package de.app.fivegla.business;

import de.app.fivegla.integration.fiware.SubscriptionIntegrationService;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionIntegrationService subscriptionIntegrationService;

    /**
     * Delete all existing subscriptions.
     *
     * @param tenant the tenant
     */
    public void deleteAllSubscriptions(Tenant tenant) {
        log.info("Deleting all subscriptions");
        subscriptionIntegrationService.removeAll(tenant);
    }

}
