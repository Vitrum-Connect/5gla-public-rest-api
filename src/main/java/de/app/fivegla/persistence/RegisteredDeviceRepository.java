package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.RegisteredDevice;
import de.app.fivegla.persistence.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * The repository for the registered devices.
 */
@Repository
public interface RegisteredDeviceRepository extends JpaRepository<RegisteredDevice, Long> {

    /**
     * Finds all registered devices for a tenant.
     *
     * @param tenant The tenant to find the registered devices for.
     * @return The list of registered devices.
     */
    List<RegisteredDevice> findAllByTenant(Tenant tenant);

    /**
     * Finds a registered device by tenant and sensor ID.
     *
     * @param tenant   The tenant to find the registered device for.
     * @param sensorId The sensor ID to find the registered device for.
     * @return The registered device.
     */
    Optional<RegisteredDevice> findByTenantAndOid(Tenant tenant, String sensorId);
}
