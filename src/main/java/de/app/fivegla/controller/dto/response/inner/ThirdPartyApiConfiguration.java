package de.app.fivegla.controller.dto.response.inner;

import de.app.fivegla.api.Manufacturer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "Response for finding all third API responses.")
public class ThirdPartyApiConfiguration {

    /**
     * Represents the tenant.
     */
    @Schema(description = "The tenant.")
    private String tenantId;

    /**
     * Represents the manufacturer.
     */
    @Schema(description = "The manufacturer.")
    private Manufacturer manufacturer;

    /**
     * Indicator if the configuration is enabled.
     */
    @Schema(description = "Indicator if the configuration is enabled.")
    private boolean enabled;

}
