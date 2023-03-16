package de.app.fivegla.integration.soilscout.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Soil scout token request.
 */
@Getter
@Builder
public class TokenRefreshRequest {

    /**
     * SSO token.
     */
    @JsonProperty("refresh")
    private final String refreshToken;

}
