package de.app.fivegla.integration.soilscout.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Soil scout SSO response.
 */
@Getter
@Setter
public class SsoResponse {

    /**
     * SSO token.
     */
    @JsonProperty("sso_token")
    private String ssoToken;
}
