package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.dto.SortableImageOids;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Optional<MicaSenseImage> getImage(String oid) {
        return applicationData.getMicaSenseImages().stream()
                .filter(image -> image.getOid().equals(oid))
                .findFirst();
    }

    /**
     * Add image to the list of images.
     *
     * @param micaSenseImage The image to add.
     * @return The added image.
     */
    public MicaSenseImage addMicaSenseImage(MicaSenseImage micaSenseImage) {
        return applicationData.addMicaSenseImage(micaSenseImage);
    }

    /**
     * Retrieves the image Object IDs (Oids) associated with a specific transaction.
     *
     * @param transactionId The ID of the transaction.
     * @return A list of image Object IDs (Oids) associated with the specified transaction.
     */
    public List<SortableImageOids> getImageOidsForTransaction(String transactionId) {
        return applicationData.getMicaSenseImages().stream()
                .filter(micaSenseImage -> micaSenseImage.getTransactionId().equals(transactionId))
                .map(micaSenseImage -> new SortableImageOids(micaSenseImage.getOid(), micaSenseImage.getMeasuredAt()))
                .toList();
    }

    /**
     * Disables a job for a specific manufacturer.
     *
     * @param manufacturer The manufacturer for which the job is disabled.
     *                     Possible values are:
     *                     - SOILSCOUT
     *                     - AGRANIMO
     *                     - FARM21
     *                     - MICA_SENSE
     *                     - AGVOLUTION
     *                     - SENSOTERRA
     *                     - SENTEK
     *                     - WEENAT
     */
    public void disableJob(Manufacturer manufacturer) {
        applicationData.disableJob(manufacturer);
    }

    /**
     * Checks if the specified job is enabled for the given manufacturer.
     *
     * @param manufacturer The manufacturer for which to check the job status.
     * @return True if the job is enabled for the manufacturer, false otherwise.
     * @see Manufacturer
     */
    public boolean isTheJobEnabled(Manufacturer manufacturer) {
        return !applicationData.isTheJobDisabled(manufacturer);
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
    public List<Tenant> findAll() {
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
        return applicationData.getThirdPartyApiConfigurations().stream().filter(configuration -> configuration.getTenantId().equals(name)).toList();
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
}