package de.app.fivegla.persistence.entity;

import de.app.fivegla.controller.dto.request.CreateGroupRequest;
import de.app.fivegla.controller.dto.request.UpdateGroupRequest;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a group of sensors or devices, created by the tenant.
 */
@Getter
@Setter
public class Group extends BaseEntity {

    /**
     * The id of the group. Should be unique within the tenant.
     */
    private String oid;

    /**
     * The name of the group.
     */
    private String name;

    /**
     * The description of the group.
     */
    private String description;

    /**
     * Represents if the group is the default group for the tenant.
     */
    private boolean defaultGroupForTenant;

    /**
     * The tenant of the group.
     */
    @OneToMany
    private Tenant tenant;

    /**
     * The sensor ids assigned to the group.
     */
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> sensorIdsAssignedToGroup;

    public static Group from(CreateGroupRequest createGroupRequest) {
        Group group = new Group();
        group.setName(createGroupRequest.getName());
        group.setDescription(createGroupRequest.getDescription());
        return group;
    }

    public static Group from(UpdateGroupRequest updateGroupRequest) {
        Group group = new Group();
        group.setOid(updateGroupRequest.getGroupId());
        group.setName(updateGroupRequest.getName());
        group.setDescription(updateGroupRequest.getDescription());
        return group;
    }
}
