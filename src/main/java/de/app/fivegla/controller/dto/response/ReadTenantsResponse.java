package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import de.app.fivegla.controller.dto.response.inner.Tenant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response for finding all tenants.
 */
@Getter
@Setter
@Builder
@Schema(name = "Response for finding all tenants.")
public class ReadTenantsResponse extends Response {

    /**
     * The list of tenants.
     */
    @Schema(description = "The list of tenants.")
    private List<Tenant> tenants;

}
