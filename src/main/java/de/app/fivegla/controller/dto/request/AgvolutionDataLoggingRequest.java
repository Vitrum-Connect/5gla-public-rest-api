package de.app.fivegla.controller.dto.request;

import de.app.fivegla.integration.agvolution.model.SeriesEntry;

/**
 * Request for data logging.
 */
public record AgvolutionDataLoggingRequest(SeriesEntry seriesEntry) {
}
