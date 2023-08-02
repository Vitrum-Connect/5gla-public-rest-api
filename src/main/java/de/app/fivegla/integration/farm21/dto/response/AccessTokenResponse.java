package de.app.fivegla.integration.farm21.dto.response;

import de.app.fivegla.integration.farm21.dto.response.inner.AccessToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Access token response.
 */
@Getter
@Setter
@Schema(description = "Access token response.")
public class AccessTokenResponse {

    /**
     * The access token.
     */
    @Schema(description = "The access token.")
    private AccessToken accessToken;
}
