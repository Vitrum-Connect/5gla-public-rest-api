package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Represents a request to update a group.")
public class UpdateGroupRequest {

    @Schema(description = "The UUID of the group.")
    private String groupId;

    @Schema(description = "The name of the group.")
    private String name;

    @Schema(description = "The description of the group.")
    private String description;
}
