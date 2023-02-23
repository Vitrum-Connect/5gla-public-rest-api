package de.app.fivegla.integration.soilscout.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Soil scout access token response.
 */
@Getter
@Setter
public class SoilScoutAccessTokenResponse {

    /**
     * Access token.
     */
    private String access;

    /**
     * Refresh token.
     */
    private String refresh;

}
