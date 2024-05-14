package de.app.fivegla.event;

import de.app.fivegla.Application;
import de.app.fivegla.api.SubscriptionStatus;
import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.event.events.ResendSubscriptionsEvent;
import de.app.fivegla.integration.fiware.SubscriptionIntegrationService;
import de.app.fivegla.integration.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartEventHandler {

    private final SubscriptionStatus subscriptionStatus;
    private final Application application;
    private final TenantService tenantService;
    private final GroupService groupService;

    @EventListener(classes = {ApplicationReadyEvent.class, ResendSubscriptionsEvent.class})
    public void triggerSubscriptionsForAllTenants() {
        log.debug("Triggering subscriptions for all tenants to ensure that they are subscribed to device measurement notifications and other entities.");
        var allTenants = tenantService.findAll();
        if (null == allTenants || allTenants.isEmpty()) {
            log.error("There are no tenants, it is not necessary to send out subscriptions");
        } else {
            allTenants.forEach(this::triggerSubscriptionsForTenant);
            allTenants.forEach(this.groupService::createDefaultGroup);
        }
    }

    private void triggerSubscriptionsForTenant(Tenant tenant) {
        var tenantId = tenant.getTenantId();
        var subscriptionService = subscriptionService(tenantId);
        if (subscriptionStatus.sendOutSubscriptions(tenantId)) {
            try {
                subscriptionService.subscribe(EntityType.values());
                log.info("Subscribed to device measurement notifications.");
                subscriptionStatus.subscriptionSent(tenantId);
            } catch (FiwareIntegrationLayerException e) {
                log.error("Could not subscribe to device measurement notifications.");
            }
        } else {
            log.info("Subscriptions are disabled. Not subscribing to device measurement notifications.");
        }
    }

    private SubscriptionIntegrationService subscriptionService(String tenantId) {
        return application.subscriptionService(tenantId);
    }

}
