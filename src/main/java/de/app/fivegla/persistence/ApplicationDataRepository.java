package de.app.fivegla.persistence;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ApplicationDataRepository {

    private final ApplicationData applicationData;

    public ApplicationDataRepository(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public Instant getLastRun() {
        if (applicationData.getLastRun() == null) {
            return null;
        } else {
            return applicationData.getLastRun();
        }
    }

    public void updateLastRun() {
        applicationData.setLastRun(Instant.now());
    }
}
