package de.app.fivegla.persistence;

import lombok.Getter;
import one.microstream.integrations.spring.boot.types.Storage;
import one.microstream.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@Storage
public class ApplicationData {

    @Autowired
    private transient StorageManager storageManager;

    @Getter
    private Instant lastRun;

    public void setLastRun(Instant lastRun) {
        this.lastRun = lastRun;
        storageManager.store(lastRun);
    }

}
