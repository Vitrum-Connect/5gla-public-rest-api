package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GroupRepository {

    private final ApplicationData applicationData;

    /**
     * Adds a group to the application data.
     *
     * @param group The group to be added.
     * @return The added group.
     */
    public Group add(Group group) {
        if (null == applicationData.getGroups()) {
            applicationData.setGroups(new ArrayList<>());
        }
        applicationData.getGroups().add(group);
        applicationData.persist();
        return group;
    }

    /**
     * Update a group.
     *
     * @param newGroupData The new group data.
     */
    public Optional<Group> update(Group newGroupData) {
        if (null == applicationData.getGroups()) {
            applicationData.setGroups(new ArrayList<>());
        }
        applicationData.getGroups().stream().filter(group -> group.getGroupId().equals(newGroupData.getGroupId()))
                .findFirst()
                .ifPresent(group -> {
                    group.setName(newGroupData.getName());
                    group.setDescription(newGroupData.getDescription());
                    group.setUpdatedAt(Instant.now());
                    group.setSensorIdsAssignedToGroup(newGroupData.getSensorIdsAssignedToGroup());
                });
        applicationData.persist();
        return get(newGroupData.getGroupId());
    }

    /**
     * Find a group by its ID.
     *
     * @param groupId The ID of the group to find.
     * @return An Optional containing the found group, or an empty Optional if no group with the specified ID is found.
     */
    public Optional<Group> get(String groupId) {
        if (null == applicationData.getGroups()) {
            return Optional.empty();
        } else {
            return applicationData.getGroups().stream()
                    .filter(group -> group.getGroupId().equals(groupId))
                    .findFirst();
        }
    }

    /**
     * Get all groups.
     *
     * @return A list of all groups.
     */
    public List<Group> getAll() {
        if (null == applicationData.getGroups()) {
            return Collections.emptyList();
        }
        return applicationData.getGroups();
    }

    /**
     * Deletes a group.
     *
     * @param groupId The ID of the group to delete.
     */
    public void delete(String groupId) {
        if (null != applicationData.getGroups()) {
            applicationData.getGroups().removeIf(group -> group.getGroupId().equals(groupId));
            applicationData.persist();
        }
    }

    /**
     * Generates a unique group ID using UUID.
     *
     * @return The generated group ID.
     */
    public String generateGroupId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Adds a sensor to a group.
     *
     * @param group    The group to which the sensor is to be added.
     * @param sensorId The ID of the sensor to be added.
     */
    public Optional<Group> addSensorToGroup(Group group, String sensorId) {
        if (null == group.getSensorIdsAssignedToGroup()) {
            group.setSensorIdsAssignedToGroup(new ArrayList<>());
        }
        group.getSensorIdsAssignedToGroup().add(sensorId);
        return update(group);
    }
}
