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

    public void update(Group newGroupData) {
        groupRepository.update(newGroupData);
    }

    public Optional<Group> get(String groupId) {
        return groupRepository.get(groupId);
    }

    public List<Group> getAll() {
        return groupRepository.getAll();
    }

    public void delete(String groupId) {
        groupRepository.delete(groupId);
    }
}
