package de.app.fivegla.event.events;

import org.springframework.context.ApplicationEvent;

/**
 * This class represents an event triggered when subscriptions need to be resent.
 * It is a subclass of the Spring Framework's ApplicationEvent class.
 * The event source is passed as an object to the constructor.
 */
public class ResendSubscriptionsEvent extends ApplicationEvent {
    public ResendSubscriptionsEvent(Object source) {
        super(source);
    }
}
