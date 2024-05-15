package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "Response wrapper.")
public class SensorAddedToGroupResponse extends Response {

    @Schema(description = "The id of the group.")
    private String groupId;

    @Schema(description = "The name of the group.")
    private String name;

    @Schema(description = "The description of the group.")
    private String description;

    @Schema(description = "The creation date of the group.")
    private String createdAt;

    @Schema(description = "The last update date of the group.")
    private String updatedAt;

    @Schema(description = "The sensor ids assigned to the group.")
    private List<String> sensorIdsAssignedToGroup;
}