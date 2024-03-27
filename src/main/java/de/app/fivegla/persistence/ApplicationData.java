package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
import de.app.fivegla.persistence.entity.DisabledJob;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.Getter;
import one.microstream.integrations.spring.boot.types.Storage;
import one.microstream.storage.types.StorageManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.*;

/**
 * Application data.
 */
@Storage
public class ApplicationData {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private transient StorageManager storageManager;

    @Getter
    private Map<Manufacturer, Instant> lastRuns;

    private List<MicaSenseImage> micaSenseImages;

    private List<DisabledJob> disabledJobs;

    private List<Tenant> tenants;

    /**
     * Update the last run.
     *
     * @param lastRun the last run
     */
    protected void setLastRun(Manufacturer manufacturer, Instant lastRun) {
        if (lastRuns == null) {
            lastRuns = new HashMap<>();
        }
        lastRuns.put(manufacturer, lastRun);
        storageManager.store(this);
    }

    /**
     * Get the last run.
     *
     * @param manufacturer The manufacturer.
     * @return The last run.
     */
    protected Optional<Instant> getLastRun(Manufacturer manufacturer) {
        if (lastRuns == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(lastRuns.get(manufacturer));
        }
    }


    /**
     * Add image to the list of images.
     *
     * @param micaSenseImage The image to add.
     * @return The added image.
     */
    protected MicaSenseImage addMicaSenseImage(MicaSenseImage micaSenseImage) {
        if (null == micaSenseImages) {
            micaSenseImages = new ArrayList<>();
        }
        micaSenseImages.add(micaSenseImage);
        storageManager.store(this);
        return micaSenseImage;
    }

    /**
     * Access the list of images.
     *
     * @return The list of images.
     */
    protected List<MicaSenseImage> getMicaSenseImages() {
        return micaSenseImages;
    }

    /**
     * Adds a disabled job for a specific manufacturer.
     *
     * @param manufacturer The manufacturer for which the job is disabled.
     * @see Manufacturer
     * @see DisabledJob
     */
    protected void disableJob(Manufacturer manufacturer) {
        if (null == disabledJobs) {
            disabledJobs = new ArrayList<>();
        }
        var disabledJob = new DisabledJob();
        disabledJob.setDisabledAt(Instant.now());
        disabledJob.setDisabledManufacturers(manufacturer);
        disabledJobs.add(disabledJob);
        storageManager.store(this);
    }

    /**
     * Checks if the specified job is disabled for the given manufacturer.
     *
     * @param manufacturer The manufacturer for which to check the job status.
     * @return True if the job is disabled for the manufacturer, false otherwise.
     * @see Manufacturer
     */
    protected boolean isTheJobDisabled(Manufacturer manufacturer) {
        if (CollectionUtils.isEmpty(disabledJobs)) {
            return false;
        } else {
            return disabledJobs.stream()
                    .anyMatch(disabledJob -> disabledJob.getDisabledManufacturers().equals(manufacturer));
        }
    }

    /**
     * Adds a tenant to the system.
     *
     * @param tenant The tenant to add.
     */
    protected Tenant addTenant(Tenant tenant) {
        if (null == tenants) {
            tenants = new ArrayList<>();
        }
        tenants.add(tenant);
        storageManager.store(this);
        return tenant;
    }

    /**
     * Retrieves a tenant with the specified UUID.
     *
     * @param uuid The UUID of the tenant to retrieve.
     * @return An Optional containing the found Tenant, or an empty Optional if no tenant with the specified UUID is found.
     */
    protected Optional<Tenant> getTenant(String uuid) {
        return tenants.stream()
                .filter(tenant -> tenant.getUuid().equals(uuid))
                .findFirst();
    }
}
