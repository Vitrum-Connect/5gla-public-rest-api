package de.app.fivegla.scheduled;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.event.DataImportEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Scheduled data import for all manufacturer.
 */
@Component
public class DataImportScheduler {

    private final ApplicationEventPublisher applicationEventPublisher;

    public DataImportScheduler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Schedule data import for all manufacturer.
     */
    @Scheduled(cron = "${app.scheduled.data-import.cron}")
    public void scheduleDataImport() {
        Arrays.stream(Manufacturer.values())
                .forEach(manufacturer -> applicationEventPublisher.publishEvent(new DataImportEvent(manufacturer)));
    }

}
