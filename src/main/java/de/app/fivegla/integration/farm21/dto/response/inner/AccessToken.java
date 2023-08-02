package de.app.fivegla.integration.farm21.dto.response.inner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Access token.
 */
@Getter
@Setter
@Schema(description = "Access token.")
public class AccessToken {

    /**
     * The ID.
     */
    @Schema(description = "The ID.")
    private String id;

    /**
     * The name.
     */
    @Schema(description = "The name.")
    private String name;

    /**
     * The plain text token.
     */
    @Schema(description = "The plain text token.")
    private String plainTextToken;

}
