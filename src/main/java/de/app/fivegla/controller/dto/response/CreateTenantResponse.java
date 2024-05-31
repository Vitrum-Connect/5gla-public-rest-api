package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The CreateTenantResponse class represents a response wrapper for creating a new tenant.
 * It extends the Response class, which is a base class for all responses.
 */
@Getter
@Setter
@Builder
@Schema(description = "Response wrapper.")
public class CreateTenantResponse extends Response {

    @Schema(description = "The name of the tenant.")
    private String name;

    @Schema(description = "The UUID of the tenant.")
    private String tenantId;

    @Schema(description = "The access token of the tenant.")
    private String accessToken;

}
