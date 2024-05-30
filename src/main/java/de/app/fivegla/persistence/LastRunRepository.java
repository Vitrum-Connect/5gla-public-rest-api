package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.persistence.entity.LastRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository for the last-run entity.
 */
@Repository
public interface LastRunRepository extends JpaRepository<LastRun, Long> {

    /**
     * Retrieves all last runs for the tenant.
     *
     * @param tenantId the id of the tenant
     * @return a list of last runs for the tenant
     */
    List<LastRun> findAllByTenantId(String tenantId);

    /**
     * Retrieves the last run for a specific manufacturer.
     *
     * @param tenantId     the id of the tenant
     * @param manufacturer the manufacturer to get the last run for
     * @return an Optional containing the last run Instant, or an empty Optional if no last run is found
     */
    Optional<LastRun> findByTenantIdAndManufacturer(String tenantId, Manufacturer manufacturer);
}
