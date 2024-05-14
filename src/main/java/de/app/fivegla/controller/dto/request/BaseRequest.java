package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a base request.
 */
@Getter
@Setter
class BaseRequest {

    /**
     * A custom group, which can be used to group devices / measurements.
     */
    @NotBlank
    @Schema(description = "A custom group ID, which can be used to group devices / measurements. This is mandatory since all devices and measurements must be assigned to a group.")
    protected String groupId;

}
