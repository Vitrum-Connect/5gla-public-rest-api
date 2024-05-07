package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a base request.
 */
@Getter
@Setter
class BaseRequest {

    /**
     * A custom zone, which can be used to group devices / measurements. This is optional.
     */
    @Schema(description = "A custom zone, which can be used to group devices / measurements. This is optional.")
    private String zone;

}
