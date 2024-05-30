package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the third-party API configuration entity.
 */
@Repository
public interface ThirdPartyApiConfigurationRepository extends JpaRepository<ThirdPartyApiConfiguration, Long> {

    /**
     * Deletes the third-party API configuration with the given tenantId and uuid.
     *
     * @param tenantId The tenantId of the third-party API configuration to delete.
     * @param uuid     The uuid of the third-party API configuration to delete.
     * @return The number of deleted third-party API configurations.
     */
    List<ThirdPartyApiConfiguration> findAllByTenantTenantIdAndUuid(String tenantId, String uuid);

    /**
     * Deletes the third-party API configuration with the given tenantId.
     *
     * @param tenantId The tenantId of the third-party API configuration to delete.
     * @param uuid     The uuid of the third-party API configuration to delete.
     */
    void deleteByTenantTenantIdAndUuid(String tenantId, String uuid);


    /**
     * Returns all third-party API configurations with the given tenantId.
     *
     * @param tenantId The tenantId of the third-party API configurations.
     * @return A list of third-party API configurations.
     */
    List<ThirdPartyApiConfiguration> findAllByTenantTenantId(String tenantId);

    /**
     * Deletes the third-party API configurations with the given tenantId.
     *
     * @param tenantId The tenantId of the third-party API configurations to delete.
     */
    void deleteByTenantTenantId(String tenantId);
}
