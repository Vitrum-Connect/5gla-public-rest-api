package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.model.micasense.MicaSenseImage;
import lombok.Getter;
import one.microstream.integrations.spring.boot.types.Storage;
import one.microstream.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Map<Manufacturer, Instant> lastRuns;

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
        lastRuns.put(manufacturer, lastRun);
        storageManager.store(this);
    }

    /**
     * Get the last run.
     *
     * @param manufacturer The manufacturer.
     * @return The last run.
     */
    public Instant getLastRun(Manufacturer manufacturer) {
        return lastRuns.get(manufacturer);
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
