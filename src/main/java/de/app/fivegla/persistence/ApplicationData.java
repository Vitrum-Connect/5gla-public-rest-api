package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import lombok.Getter;
import one.microstream.integrations.spring.boot.types.Storage;
import one.microstream.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.HashMap;
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

    public Instant getLastRun(Manufacturer manufacturer) {
        return lastRuns.get(manufacturer);
    }

}
