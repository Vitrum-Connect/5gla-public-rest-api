package de.app.fivegla.event.events;

import de.app.fivegla.persistence.entity.Tenant;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * This class represents an event triggered when a default group needs to be created for a tenant.
 * It extends the ApplicationEvent class.
 */
@Getter
public class CreateDefaultGroupForTenantEvent extends ApplicationEvent {

    private final Tenant tenant;

    public CreateDefaultGroupForTenantEvent(Object source, Tenant tenant) {
        super(source);
        this.tenant = tenant;
    }

}
