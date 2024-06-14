package de.app.fivegla.event.events;

/**
 * Event for data import.
 *
 * @param thirdPartyApiConfigurationId The ID of the third party API configuration.
 */
public record DataImportEvent(Long thirdPartyApiConfigurationId) {
}
