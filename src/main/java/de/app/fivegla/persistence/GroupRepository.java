package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the group entity.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Returns the group with the given oid.
     *
     * @param oid The oid of the group.
     * @return The group with the given oid.
     */
    Optional<Group> findByOid(String oid);

    /**
     * Returns a list of all groups associated with the specified tenant.
     *
     * @param tenant The tenant for which to find the groups.
     * @return A list of Group objects associated with the specified tenant.
     */
    List<Group> findAllByTenant(Tenant tenant);

    /**
     * Finds all groups that belong to a specific tenant and have the defaultGroupForTenant attribute set to true.
     *
     * @param tenant The tenant for which to find the groups.
     * @return A list of Group objects that belong to the specified tenant and have defaultGroupForTenant set to true.
     */
    Optional<Group> findByTenantAndDefaultGroupForTenantIsTrue(Tenant tenant);

    /**
     * Deletes the group with the given oid.
     *
     * @param oid The oid of the group to delete.
     */
    void deleteByOid(String oid);

    /**
     * Checks if a group exists with the given tenant and the group is the default group for the tenant.
     *
     * @param tenant The tenant for which to check the group existence.
     * @return true if a group exists with the given tenant and it is the default group, false otherwise.
     */
    boolean existsByTenantAndDefaultGroupForTenantIsTrue(Tenant tenant);

    /**
     * Finds a group based on the tenant and sensor IDs assigned to the group containing the specified sensor ID.
     *
     * @param tenant   The tenant for which to find the group.
     * @param sensorId The sensor ID to search for in the group.
     * @return The group with the specified sensor ID assigned, or null if not found.
     */
    Optional<Group> findByTenantAndSensorIdsAssignedToGroupContaining(Tenant tenant, String sensorId);
}
