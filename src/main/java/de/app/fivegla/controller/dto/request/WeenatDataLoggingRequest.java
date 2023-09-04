package de.app.fivegla.controller.dto.request;

import de.app.fivegla.integration.weenat.model.Measurement;

import java.util.List;

/**
 * Request for data logging.
 */
public record WeenatDataLoggingRequest(List<Measurement> measurements) {
}
