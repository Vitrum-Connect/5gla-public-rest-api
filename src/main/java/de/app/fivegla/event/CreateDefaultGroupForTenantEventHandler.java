package de.app.fivegla.event;

import de.app.fivegla.business.GroupService;
import de.app.fivegla.event.events.CreateDefaultGroupForTenantEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateDefaultGroupForTenantEventHandler {

    private final GroupService groupService;

    /**
     * This method handles the event of creating a default group for a tenant.
     * It retrieves the tenant from the event, creates a default group for the tenant,
     * and adds it to the application data through the group repository.
     *
     * @param createDefaultGroupForTenantEvent The event object containing the tenant for which the default group needs to be created.
     */
    @EventListener(CreateDefaultGroupForTenantEvent.class)
    public void handleCreateDefaultGroupForTenantEvent(CreateDefaultGroupForTenantEvent createDefaultGroupForTenantEvent) {
        log.info("Handling create default group for tenant '{}'.", createDefaultGroupForTenantEvent.getTenant().getTenantId());
        groupService.createDefaultGroup(createDefaultGroupForTenantEvent.getTenant());
    }
}
