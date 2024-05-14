package de.app.fivegla.persistence;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TenantRepository {

    private final ApplicationData applicationData;

    /**
     * Updates the tenant with the provided tenantId.
     *
     * @param tenantId    The tenantId of the tenant to update.
     * @param name        The new name of the tenant.
     * @param description The new description of the tenant.
     * @return The updated tenant.
     */
    public Tenant updateTenant(String tenantId, String name, String description) {
        var tenant = applicationData.getTenants().stream()
                .filter(t -> t.getTenantId().equals(tenantId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                        .error(Error.TENANT_NOT_FOUND)
                        .message("Could not update tenant, since the tenant was not found.")
                        .build()));
        tenant.setName(name);
        tenant.setDescription(description);
        applicationData.persist();
        return tenant;
    }

    /**
     * Adds a tenant to the system.
     *
     * @param tenant The tenant to add.
     * @return The added tenant.
     */
    public Tenant addTenant(Tenant tenant) {
        if (null == applicationData.getTenants()) {
            applicationData.setTenants(new ArrayList<>());
        }
        applicationData.getTenants().add(tenant);
        applicationData.persist();
        return tenant;
    }

    /**
     * Retrieves a tenant with the specified tenant ID.
     *
     * @param tenantId The ID of the tenant to retrieve.
     * @return An Optional containing the found Tenant, or an empty Optional if no tenant with the specified tenant ID is found.
     */
    public Optional<Tenant> getTenant(String tenantId) {
        if (null == applicationData.getTenants()) {
            return Optional.empty();
        } else {
            return applicationData.getTenants().stream()
                    .filter(tenant -> tenant.getTenantId().equals(tenantId))
                    .findFirst();
        }
    }

    /**
     * Finds all tenants in the system.
     *
     * @return A list of all tenants in the system.
     */
    public List<Tenant> findTenants() {
        return applicationData.getTenants();
    }
}
