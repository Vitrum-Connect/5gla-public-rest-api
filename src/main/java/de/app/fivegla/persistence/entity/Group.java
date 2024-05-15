package de.app.fivegla.persistence.entity;

import de.app.fivegla.controller.dto.request.CreateGroupRequest;
import de.app.fivegla.controller.dto.request.UpdateGroupRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

/**
 * Represents a group of sensors or devices, created by the tenant.
 */
@Getter
@Setter
public class Group {

    /**
     * The id of the group. Should be unique within the tenant.
     */
    private String groupId;

    /**
     * The tenant of the group.
     */
    private Tenant tenant;

    /**
     * The name of the group.
     */
    private String name;

    /**
     * The description of the group.
     */
    private String description;

    /**
     * The creation date of the group.
     */
    private Instant createdAt;

    /**
     * The last update date of the group.
     */
    private Instant updatedAt;

    /**
     * Represents if the group is the default group for the tenant.
     */
    private boolean defaultGroupForTenant;

    /**
     * The sensor ids assigned to the group.
     */
    private List<String> sensorIdsAssignedToGroup;

    public static Group from(CreateGroupRequest createGroupRequest) {
        Group group = new Group();
        group.setName(createGroupRequest.getName());
        group.setDescription(createGroupRequest.getDescription());
        return group;
    }

    public static Group from(UpdateGroupRequest updateGroupRequest) {
        Group group = new Group();
        group.setGroupId(updateGroupRequest.getGroupId());
        group.setName(updateGroupRequest.getName());
        group.setDescription(updateGroupRequest.getDescription());
        return group;
    }
}
