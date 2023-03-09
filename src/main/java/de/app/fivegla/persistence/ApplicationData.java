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
    private LastRun lastRun;

    public void setLastRun(Instant lastRun) {
        var newLastRun = new LastRun();
        newLastRun.setLastRun(lastRun);
        this.lastRun = newLastRun;
        storageManager.store(this);
    }

}
