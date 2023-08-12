package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.api.ManufacturerType;
import de.app.fivegla.integration.micasense.model.MicaSenseImage;
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
    private Map<ManufacturerType, Instant> lastRuns;

    private List<MicaSenseImage> micaSenseImages;

    /**
     * Update the last run.
     *
     * @param lastRun the last run
     */
    public void setLastRun(Manufacturer manufacturer, Instant lastRun) {
        if (lastRuns == null) {
            lastRuns = new HashMap<>();
        }
        lastRuns.put(manufacturer.getManufacturerType(), lastRun);
        storageManager.store(this);
    }

    /**
     * Get the last run.
     *
     * @param manufacturer The manufacturer.
     * @return The last run.
     */
    public Optional<Instant> getLastRun(Manufacturer manufacturer) {
        if (lastRuns == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(lastRuns.get(manufacturer.getManufacturerType()));
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

}
