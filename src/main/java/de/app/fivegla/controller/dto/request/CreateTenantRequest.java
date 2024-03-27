package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request for tenant creation.
 */
@Getter
@Setter
@Schema(description = "Represents a request for tenant creation.")
public class CreateTenantRequest {

    /**
     * The name of the tenant.
     */
    @NotBlank
    @Schema(description = "The name of the tenant.")
    private String name;

    /**
     * The description of the tenant, the description optional.
     */
    @Schema(description = "The description of the tenant, the description optional.")
    private String description;

}
