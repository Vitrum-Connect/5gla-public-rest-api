package de.app.fivegla.integration.soilscout.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * Soil scout SSO request.
 */
@Getter
@Builder
public class SoilScoutSsoRequest {

    /**
     * Username.
     */
    private final String username;

    /**
     * Password.
     */
    private final String password;

}
