package de.app.fivegla.controller.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Response for the version endpoint.
 */
@Builder
public class VersionResponse extends Response {

    @Getter
    private String version;

}
