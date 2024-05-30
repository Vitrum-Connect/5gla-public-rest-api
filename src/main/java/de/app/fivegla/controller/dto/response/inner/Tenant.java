package de.app.fivegla.controller.dto.response.inner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "The tenant.")
public class Tenant {

    /**
     * The name of the tenant.
     */
    @Schema(description = "The name of the tenant.")
    private String name;

    /**
     * The description of the tenant.
     */
    @Schema(description = "The description of the tenant.")
    private String description;

    /**
     * The UUID of the tenant.
     */
    @Schema(description = "The UUID of the tenant.")
    private String tenantId;

}
