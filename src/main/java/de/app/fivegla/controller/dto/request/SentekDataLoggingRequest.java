package de.app.fivegla.controller.dto.request;

import de.app.fivegla.integration.sentek.model.csv.Reading;

import java.util.List;

/**
 * Request for data logging.
 */
public record SentekDataLoggingRequest(List<Reading> readings) {
}
