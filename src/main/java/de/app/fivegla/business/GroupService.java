package de.app.fivegla.business;


import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.persistence.GroupRepository;
import de.app.fivegla.persistence.TenantRepository;
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
    private final TenantRepository tenantRepository;

    /**
     * Adds a group to the application.
     *
     * @param group The group to be added.
     * @return The added group.
     */
    public Group add(String tenantId, Group group) {
        var tenant = tenantRepository.getTenant(tenantId).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.TENANT_NOT_FOUND)
                .message("Could not add group, since the tenant was not found.")
                .build()));
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
    public Optional<Group> update(String tenantId, Group newGroupData) {
        var group = groupRepository.get(newGroupData.getGroupId()).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.GROUP_NOT_FOUND)
                .message("Could not update group, since the group was not found.")
                .build()));
        if (!group.getTenant().getTenantId().equals(tenantId)) {
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
    public Optional<Group> get(String tenantId, String groupId) {
        var optionalGroup = groupRepository.get(groupId);
        if (optionalGroup.isPresent() && !optionalGroup.get().getTenant().getTenantId().equals(tenantId)) {
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
    public List<Group> getAll(String tenantId) {
        return groupRepository.getAll().stream().filter(group -> group.getTenant().getTenantId().equals(tenantId)).toList();
    }

    /**
     * Deletes a group by its ID.
     *
     * @param groupId The ID of the group to delete.
     */
    public void delete(String tenantId, String groupId) {
        var group = groupRepository.get(groupId).orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                .error(Error.GROUP_NOT_FOUND)
                .message("Could not update group, since the group was not found.")
                .build()));
        if (!group.getTenant().getTenantId().equals(tenantId)) {
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
}
