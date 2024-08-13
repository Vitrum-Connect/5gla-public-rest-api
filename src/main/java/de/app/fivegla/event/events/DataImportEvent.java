package de.app.fivegla.event.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event for data import.
 */
@Getter
public class DataImportEvent extends ApplicationEvent {

    private final Long thirdPartyApiConfigurationId;

    public DataImportEvent(Object source, Long thirdPartyApiConfigurationId) {
        super(source);
        this.thirdPartyApiConfigurationId = thirdPartyApiConfigurationId;
    }
}
