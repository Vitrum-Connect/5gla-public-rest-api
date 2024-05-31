package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the tenant entity.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    /**
     * Returns the tenant with the given tenantId.
     *
     * @param tenantId The tenantId of the tenant.
     * @return The tenant with the given tenantId.
     */
    Optional<Tenant> findByTenantId(String tenantId);

    /**
     * Deletes the tenant with the given tenantId.
     *
     * @param tenantId The tenantId of the tenant to delete.
     */
    void deleteByTenantId(String tenantId);
}
