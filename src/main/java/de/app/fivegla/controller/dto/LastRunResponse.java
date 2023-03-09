package de.app.fivegla.controller.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Response for the last run endpoint.
 */
@Builder
public class LastRunResponse extends Response {

    @Getter
    private final String lastRun;

}
