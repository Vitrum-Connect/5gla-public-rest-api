package de.app.fivegla.business;


import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.persistence.GroupRepository;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    /**
     * Adds a group to the application.
     *
     * @param group The group to be added.
     * @return The added group.
     */
    public Group add(Tenant tenant, Group group) {
        group.setGroupId(groupRepository.generateGroupId());
        group.setTenant(tenant);
        group.setCreatedAt(Instant.now());
        group.setUpdatedAt(Instant.now());
        log.info("Adding group with name: {} and description: {}", group.getName(), group.getDescription());
        return groupRepository.add(group);
    }

    /**
     * Updates the group data with new information.
     *
     * @param newGroupData The new group data.
     * @return An Optional containing the updated group if it exists, or an empty Optional if the group doesn't exist.
     */
    public Optional<Group> update(Tenant tenant, Group newGroupData) {
        var group = groupRepository.get(newGroupData.getGroupId()).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.GROUP_NOT_FOUND)
                .message("Could not update group, since the group was not found.")
                .build()));
        if (!group.getTenant().getTenantId().equals(tenant.getTenantId())) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.TRYING_TO_UPDATE_GROUP_FROM_ANOTHER_TENANT)
                    .message("Could not update group, since the group is from another tenant.")
                    .build());
        }
        return groupRepository.update(newGroupData);
    }

    /**
     * Retrieves a group by its ID.
     *
     * @param groupId The ID of the group to retrieve.
     * @return An Optional containing the retrieved group, or an empty Optional if no group with the specified ID is found.
     */
    public Optional<Group> get(Tenant tenant, String groupId) {
        var optionalGroup = groupRepository.get(groupId);
        if (optionalGroup.isPresent() && !optionalGroup.get().getTenant().getTenantId().equals(tenant.getTenantId())) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.TRYING_TO_ACCESS_GROUP_FROM_ANOTHER_TENANT)
                    .message("Could not get group, since the group is from another tenant.")
                    .build());
        } else {
            return optionalGroup;
        }
    }

    /**
     * Retrieves all groups.
     *
     * @return A list of all groups.
     */
    public List<Group> getAll(Tenant tenant) {
        return groupRepository.getAll().stream().filter(group -> group.getTenant().getTenantId().equals(tenant.getTenantId())).toList();
    }

    /**
     * Deletes a group by its ID.
     *
     * @param groupId The ID of the group to delete.
     */
    public void delete(Tenant tenant, String groupId) {
        var group = groupRepository.get(groupId).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.GROUP_NOT_FOUND)
                .message("Could not update group, since the group was not found.")
                .build()));
        if (!group.getTenant().getTenantId().equals(tenant.getTenantId())) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.TRYING_TO_DELETE_GROUP_FROM_ANOTHER_TENANT)
                    .message("Could not delete group, since the group is from another tenant.")
                    .build());
        } else {
            groupRepository.delete(groupId);
        }
    }

    public void createDefaultGroup(Tenant tenant) {
        if (!checkIfThereIsAlreadyADefaultGroup(tenant)) {
            Group group = new Group();
            group.setGroupId(groupRepository.generateGroupId());
            group.setTenant(tenant);
            group.setName("Default Group");
            group.setDescription("The default group for the tenant.");
            group.setDefaultGroupForTenant(true);
            group.setCreatedAt(Instant.now());
            group.setUpdatedAt(Instant.now());
            groupRepository.add(group);
        } else {
            log.info("Default group already exists for tenant with ID: {}", tenant.getTenantId());
        }
    }

    private boolean checkIfThereIsAlreadyADefaultGroup(Tenant tenant) {
        return groupRepository.getAll().stream()
                .anyMatch(group -> group.getTenant().equals(tenant) && group.isDefaultGroupForTenant());
    }

    /**
     * Retrieves the group with the specified group ID for the given tenant. If the group does not exist, a default group is created for the tenant and returned.
     *
     * @param tenant  The tenant for which to retrieve the group.
     * @param groupId The ID of the group to retrieve.
     * @return The group with the specified group ID for the given tenant, or a default group if the group does not exist.
     * @throws BusinessException If the default group for the tenant cannot be found.
     */
    public Group getOrDefault(Tenant tenant, String groupId) {
        var optionalGroup = groupRepository.get(groupId);
        return optionalGroup.orElseGet(() -> groupRepository.getAll().stream()
                .filter(group -> group.getTenant().equals(tenant) && group.isDefaultGroupForTenant())
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                        .error(Error.DEFAULT_GROUP_FOR_TENANT_NOT_FOUND)
                        .message("Could not find the default group for the tenant.")
                        .build())));
    }

    /**
     * Retrieves the default group for a given tenant.
     *
     * @param tenant the tenant for which to retrieve the default group
     * @return the default group for the tenant
     * @throws BusinessException if the default group for the tenant is not found
     */
    public Group getDefaultGroupForTenant(Tenant tenant) {
        return groupRepository.getAll().stream()
                .filter(group -> group.getTenant().equals(tenant) && group.isDefaultGroupForTenant())
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                        .error(Error.DEFAULT_GROUP_FOR_TENANT_NOT_FOUND)
                        .message("Could not find the default group for the tenant.")
                        .build()));
    }

    /**
     * Assigns a sensor to an existing group within a specified tenant.
     *
     * @param tenant   The tenant owning the group and sensor.
     * @param groupId  The ID of the group to assign the sensor to.
     * @param sensorId The ID of the sensor to assign to the group.
     * @return An {@link Optional} containing the updated group if assignment is successful,
     * otherwise returns an empty {@link Optional}.
     * @throws BusinessException If the group is not found.
     */
    public Optional<Group> assignSensorToExistingGroup(Tenant tenant, String groupId, String sensorId) {
        var group = get(tenant, groupId).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.GROUP_NOT_FOUND)
                .message("Could not assign sensor to group, since the group was not found.")
                .build()));
        return groupRepository.addSensorToGroup(group, sensorId);
    }

    /**
     * Finds a group by tenant and sensor ID.
     *
     * @param tenant   The tenant object to filter by.
     * @param sensorId The sensor ID to filter by.
     * @return The group found based on the given tenant and sensor ID,
     * or the default group for the tenant if no group is found.
     */
    public Group findGroupByTenantAndSensorId(Tenant tenant, String sensorId) {
        return groupRepository.getAll().stream()
                .filter(group -> group.getTenant().equals(tenant) && group.getSensorIdsAssignedToGroup().contains(sensorId))
                .findFirst()
                .orElse(getDefaultGroupForTenant(tenant));
    }

    /**
     * Unassigns a sensor from an existing group.
     *
     * @param tenant The tenant to which the group belongs.
     * @param groupId The ID of the group.
     * @param sensorId The ID of the sensor to be unassigned.
     * @return A Optional<Group> containing the updated group without the sensor,
     *         or an empty Optional if the group or sensor was not found.
     * @throws BusinessException If the group was not found.
     */
    public Optional<Group> unassignSensorFromExistingGroup(Tenant tenant, String groupId, String sensorId) {
        var group = get(tenant, groupId).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.GROUP_NOT_FOUND)
                .message("Could not unassign sensor from group, since the group was not found.")
                .build()));
        return groupRepository.removeSensorFromGroup(group, sensorId);
    }

    /**
     * Reassigns a sensor to an existing group.
     *
     * @param tenant    The tenant to which the group belongs.
     * @param groupId   The ID of the group.
     * @param sensorId  The ID of the sensor to be reassigned.
     * @return An Optional object containing the updated group if reassignment is successful, or empty if the group was not found.
     * @throws BusinessException   If the group is not found.
     */
    public Optional<Group> reAssignSensorToExistingGroup(Tenant tenant, String groupId, String sensorId) {
        var group = get(tenant, groupId).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.GROUP_NOT_FOUND)
                .message("Could not reassign sensor to group, since the group was not found.")
                .build()));
        groupRepository.removeSensorFromAllGroups(sensorId);
        return groupRepository.addSensorToGroup(group, sensorId);
    }
}
