package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Represents a request for group creation.")
public class CreateGroupRequest {

    /**
     * The name of the group.
     */
    @Schema(description = "The name of the group.")
    private String name;

    /**
     * The description of the group.
     */
    @Schema(description = "The description of the group.")
    private String description;
}
