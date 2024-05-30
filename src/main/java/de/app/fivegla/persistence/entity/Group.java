package de.app.fivegla.persistence.entity;

import de.app.fivegla.controller.dto.request.CreateGroupRequest;
import de.app.fivegla.controller.dto.request.UpdateGroupRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a group of sensors or devices, created by the tenant.
 */
@Entity
@Getter
@Setter
@Table(name = "group_for_tenant")
public class Group extends BaseEntity {

    /**
     * The id of the group. Should be unique within the tenant.
     */
    @Column(name = "oid", nullable = false, unique = true)
    private String oid;

    /**
     * The name of the group.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description of the group.
     */
    @Column(name = "description")
    private String description;

    /**
     * Represents if the group is the default group for the tenant.
     */
    @Column(name = "default_group_for_tenant", nullable = false)
    private boolean defaultGroupForTenant;

    /**
     * The tenant of the group.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id", nullable = false)
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
