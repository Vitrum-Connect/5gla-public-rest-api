package de.app.fivegla.controller.dto.response.inner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(name = "The group.")
public class Group {

    /**
     * The creation date of the group.
     */
    @Schema(description = "The creation date of the group.")
    private String createdAt;

    /**
     * The last update date of the group.
     */
    @Schema(description = "The last update date of the group.")
    private String updatedAt;

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

    /**
     * The UUID of the group.
     */
    @Schema(description = "The UUID of the group.")
    private String groupId;

    /**
     * The sensor ids assigned to the group.
     */
    @Schema(description = "The sensor ids assigned to the group.")
    private List<String> sensorIdsAssignedToGroup;
}
