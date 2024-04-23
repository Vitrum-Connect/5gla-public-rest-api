package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.imageprocessing.model.Image;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.Getter;
import one.microstream.integrations.spring.boot.types.Storage;
import one.microstream.storage.types.StorageManager;
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

    private List<Image> images;

    @Getter
    private List<Tenant> tenants;

    @Getter
    private List<ThirdPartyApiConfiguration> thirdPartyApiConfigurations;

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
     * @param image The image to add.
     * @return The added image.
     */
    protected Image addImage(Image image) {
        if (null == images) {
            images = new ArrayList<>();
        }
        images.add(image);
        storageManager.store(this);
        return image;
    }

    /**
     * Access the list of images.
     *
     * @return The list of images.
     */
    protected List<Image> getImages() {
        return images;
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
        if (this.tenants == null) {
            return Optional.empty();
        } else {
            return tenants.stream()
                    .filter(tenant -> tenant.getTenantId().equals(uuid))
                    .findFirst();
        }
    }

    /**
     * Adds a third-party API configuration to the system.
     *
     * @param configuration The configuration to add.
     */
    protected void addThirdPartyApiConfiguration(ThirdPartyApiConfiguration configuration) {
        if (null == thirdPartyApiConfigurations) {
            thirdPartyApiConfigurations = new ArrayList<>();
        }
        thirdPartyApiConfigurations.add(configuration);
        storageManager.store(this);
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param tenantId     The tenantId of the third-party API configuration.
     * @param manufacturer The manufacturer of the third-party API configuration.
     */
    public void deleteThirdPartyApiConfiguration(String tenantId, Manufacturer manufacturer) {
        if (null != thirdPartyApiConfigurations) {
            thirdPartyApiConfigurations.removeIf(configuration -> configuration.getTenantId().equals(tenantId) && configuration.getManufacturer().equals(manufacturer));
            storageManager.store(this);
        }
    }
}
