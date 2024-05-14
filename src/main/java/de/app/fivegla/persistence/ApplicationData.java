package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.imageprocessing.model.Image;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.Getter;
import lombok.Setter;
import one.microstream.integrations.spring.boot.types.Storage;
import one.microstream.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Application data.
 */
@Storage
public class ApplicationData {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private transient StorageManager storageManager;

    @Getter
    @Setter
    private Map<Manufacturer, Instant> lastRuns;

    private List<Image> images;

    @Getter
    @Setter
    private List<Tenant> tenants;

    @Getter
    @Setter
    private List<ThirdPartyApiConfiguration> thirdPartyApiConfigurations;

    @Getter
    @Setter
    private List<Group> groups;

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

    protected void persist() {
        storageManager.store(this);
    }

}
