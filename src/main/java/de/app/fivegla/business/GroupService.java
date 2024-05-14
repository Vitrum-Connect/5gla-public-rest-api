package de.app.fivegla.business;


import de.app.fivegla.persistence.GroupRepository;
import de.app.fivegla.persistence.entity.Group;
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
    public Group add(Group group) {
        group.setGroupId(groupRepository.generateGroupId());
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
    public Optional<Group> update(Group newGroupData) {
        return groupRepository.update(newGroupData);
    }

    /**
     * Retrieves a group by its ID.
     *
     * @param groupId The ID of the group to retrieve.
     * @return An Optional containing the retrieved group, or an empty Optional if no group with the specified ID is found.
     */
    public Optional<Group> get(String groupId) {
        return groupRepository.get(groupId);
    }

    /**
     * Retrieves all groups.
     *
     * @return A list of all groups.
     */
    public List<Group> getAll() {
        return groupRepository.getAll();
    }

    /**
     * Deletes a group by its ID.
     *
     * @param groupId The ID of the group to delete.
     */
    public void delete(String groupId) {
        groupRepository.delete(groupId);
    }
}
