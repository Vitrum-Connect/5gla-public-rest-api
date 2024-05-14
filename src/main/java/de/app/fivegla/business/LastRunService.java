package de.app.fivegla.business;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.persistence.LastRunRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastRunService {

    private final LastRunRepository lastRunRepository;


    /**
     * Retrieves the last runs for each manufacturer.
     *
     * @return a map containing the last runs for each manufacturer
     */
    public Map<Manufacturer, Instant> getLastRuns() {
        return lastRunRepository.getLastRuns();
    }

    /**
     * Retrieves the last run for a specific manufacturer.
     *
     * @param manufacturer the manufacturer to get the last run for
     * @return an Optional containing the last run Instant, or an empty Optional if no last run is found
     */
    public Optional<Instant> getLastRun(Manufacturer manufacturer) {
        return lastRunRepository.getLastRun(manufacturer);
    }

    /**
     * Update the last run for a specific manufacturer.
     *
     * @param manufacturer The manufacturer to update the last run for.
     */
    public void updateLastRun(Manufacturer manufacturer) {
        lastRunRepository.updateLastRun(manufacturer);
    }
}
