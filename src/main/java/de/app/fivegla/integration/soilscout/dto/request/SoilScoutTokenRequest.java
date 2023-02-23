package de.app.fivegla.integration.soilscout.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Soil scout token request.
 */
@Getter
@Builder
public class SoilScoutTokenRequest {

    /**
     * SSO token.
     */
    @JsonProperty("sso_token")
    private final String ssoToken;

}
