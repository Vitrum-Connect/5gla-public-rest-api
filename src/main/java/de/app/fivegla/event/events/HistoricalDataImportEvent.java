package de.app.fivegla.event.events;

import java.time.Instant;

/**
 * Event for data import.
 *
 * @param thirdPartyApiConfigurationId The ID of the third party API configuration.
 */
public record HistoricalDataImportEvent(Long thirdPartyApiConfigurationId, Instant startDate) {
}
