package de.app.fivegla.scheduled;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.event.DataImportEvent;
import de.app.fivegla.persistence.ApplicationDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Scheduled data import for all manufacturers.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataImportScheduler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationConfiguration applicationConfiguration;
    private final ApplicationDataRepository applicationDataRepository;

    /**
     * Schedule data import for all manufacturer.
     */
    @Scheduled(initialDelayString = "${app.scheduled.data-import.initial-delay}", fixedDelayString = "${app.scheduled.data-import.delay}")
    public void scheduleDataImport() {
        Arrays.stream(Manufacturer.values())
                .forEach(manufacturer -> {
                    if (applicationConfiguration.isEnabled(manufacturer) && applicationDataRepository.isTheJobEnabled(manufacturer)) {
                        applicationEventPublisher.publishEvent(new DataImportEvent(manufacturer));
                    } else {
                        log.warn("Skipping data import for manufacturer since the job is enabled by configuration or manually: {}", manufacturer);
                    }
                })
        ;
    }

}
