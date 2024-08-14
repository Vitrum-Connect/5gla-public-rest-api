package de.app.fivegla.event.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;

/**
 * Event for data import.
 */
@Getter
public class HistoricalDataImportEvent extends ApplicationEvent {

    private final Long thirdPartyApiConfigurationId;
    private final Instant startDate;

    /**
     * Constructor.
     *
     * @param thirdPartyApiConfigurationId The ID of the third party API configuration.
     * @param startDate                    The start date of the data import.
     */
    public HistoricalDataImportEvent(Object source, Long thirdPartyApiConfigurationId, Instant startDate) {
        super(source);
        this.thirdPartyApiConfigurationId = thirdPartyApiConfigurationId;
        this.startDate = startDate;
    }
}
