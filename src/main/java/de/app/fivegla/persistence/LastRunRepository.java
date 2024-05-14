package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LastRunRepository {

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
        if (applicationData.getLastRuns() == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(applicationData.getLastRuns().get(manufacturer));
        }

    }

    /**
     * Update last run.
     *
     * @param manufacturer the manufacturer
     */
    public void updateLastRun(Manufacturer manufacturer) {
        if (applicationData.getLastRuns() == null) {
            applicationData.setLastRuns(new HashMap<>());
        }
        applicationData.getLastRuns().put(manufacturer, Instant.now());
    }

}
