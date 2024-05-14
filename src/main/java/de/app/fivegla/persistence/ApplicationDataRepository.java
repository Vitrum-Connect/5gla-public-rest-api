package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.dto.SortableImageOids;
import de.app.fivegla.integration.imageprocessing.model.Image;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents a repository for application data.
 */
@Component
@RequiredArgsConstructor
public class ApplicationDataRepository {

    private final ApplicationData applicationData;

    /**
     * Gets last runs.
     *
     * @return the last runs
     */
    public Map<Manufacturer, Instant> getLastRuns() {
        return applicationData.getLastRuns();
    }

    /**
     * Get last run.
     *
     * @param manufacturer the manufacturer
     * @return the last run
     */
    public Optional<Instant> getLastRun(Manufacturer manufacturer) {
        return applicationData != null ? applicationData.getLastRun(manufacturer) : Optional.empty();
    }

    /**
     * Update last run.
     *
     * @param manufacturer the manufacturer
     */
    public void updateLastRun(Manufacturer manufacturer) {
        applicationData.setLastRun(manufacturer, Instant.now());
    }

    /**
     * Returns the image with the given oid.
     *
     * @param oid The oid of the image.
     * @return The image with the given oid.
     */
    public Optional<Image> getImage(String oid) {
        return applicationData.getImages().stream()
                .filter(image -> image.getOid().equals(oid))
                .findFirst();
    }

    /**
     * Add image to the list of images.
     *
     * @param image The image to add.
     * @return The added image.
     */
    public Image addImage(Image image) {
        return applicationData.addImage(image);
    }

    /**
     * Retrieves the image Object IDs (Oids) associated with a specific transaction.
     *
     * @param transactionId The ID of the transaction.
     * @return A list of image Object IDs (Oids) associated with the specified transaction.
     */
    public List<SortableImageOids> getImageOidsForTransaction(String transactionId) {
        return applicationData.getImages().stream()
                .filter(image -> image.getTransactionId().equals(transactionId))
                .map(image -> new SortableImageOids(image.getOid(), image.getMeasuredAt()))
                .toList();
    }

    /**
     * Adds a tenant to the system.
     *
     * @param tenant The tenant to add.
     * @return The added tenant.
     */
    public Tenant addTenant(Tenant tenant) {
        return applicationData.addTenant(tenant);
    }

    /**
     * Retrieves a tenant with the specified tenant ID.
     *
     * @param tenantId The ID of the tenant to retrieve.
     * @return An Optional containing the found Tenant, or an empty Optional if no tenant with the specified tenant ID is found.
     */
    public Optional<Tenant> getTenant(String tenantId) {
        return applicationData.getTenant(tenantId);
    }

    /**
     * Finds all tenants in the system.
     *
     * @return A list of all tenants in the system.
     */
    public List<Tenant> findTenants() {
        return applicationData.getTenants();

    }

    /**
     * Add third party API configuration.
     */
    public void addThirdPartyApiConfiguration(ThirdPartyApiConfiguration configuration) {
        applicationData.addThirdPartyApiConfiguration(configuration);
    }

    /**
     * Retrieves a list of ThirdPartyApiConfigurations based on the provided tenant ID.
     *
     * @param name The ID of the tenant.
     * @return A list of ThirdPartyApiConfigurations that match the given tenant ID.
     */
    public List<ThirdPartyApiConfiguration> getThirdPartyApiConfigurations(String name) {
        if (applicationData.getThirdPartyApiConfigurations() == null) {
            return Collections.emptyList();
        } else {
            return applicationData.getThirdPartyApiConfigurations().stream().filter(configuration -> configuration.getTenantId().equals(name)).toList();
        }
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param tenantId     The ID of the tenant for which to delete the third-party API configuration.
     * @param manufacturer The manufacturer of the third-party API configuration.
     */
    public void deleteThirdPartyApiConfiguration(String tenantId, Manufacturer manufacturer) {
        applicationData.deleteThirdPartyApiConfiguration(tenantId, manufacturer);
    }

    /**
     * Updates the tenant with the provided tenantId.
     *
     * @param tenantId    The tenantId of the tenant to update.
     * @param name        The new name of the tenant.
     * @param description The new description of the tenant.
     * @return The updated tenant.
     */
    public Tenant updateTenant(String tenantId, String name, String description) {
        return applicationData.updateTenant(tenantId, name, description);
    }

}