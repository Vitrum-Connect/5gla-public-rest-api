package de.app.fivegla.business;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.persistence.LastRunRepository;
import de.app.fivegla.persistence.entity.LastRun;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastRunService {

    private final LastRunRepository lastRunRepository;

    /**
     * Retrieves the last run for a specific manufacturer.
     *
     * @param manufacturer the manufacturer to get the last run for
     * @return an Optional containing the last run Instant, or an empty Optional if no last run is found
     */
    public Optional<LastRun> getLastRun(String tenantId, Manufacturer manufacturer) {
        return lastRunRepository.findByTenantIdAndManufacturer(tenantId, manufacturer);
    }

    /**
     * Update the last run for a specific manufacturer.
     *
     * @param manufacturer The manufacturer to update the last run for.
     */
    public void updateLastRun(String tenantId, Manufacturer manufacturer) {
        var now = Date.from(Instant.now());
        lastRunRepository.findByTenantIdAndManufacturer(tenantId, manufacturer)
                .ifPresentOrElse(lastRun -> {
                    log.info("Updating last run for manufacturer: {} to: {}", manufacturer, now);
                    lastRun.setLastRun(now);
                    lastRunRepository.save(lastRun);
                }, () -> {
                    log.info("Creating last run for manufacturer: {} with: {}", manufacturer, now);
                    var lastRun = new LastRun();
                    lastRun.setTenantId(tenantId);
                    lastRun.setManufacturer(manufacturer);
                    lastRun.setLastRun(now);
                    lastRunRepository.save(lastRun);
                });
    }
}
