package de.app.fivegla.monitoring;

import de.app.fivegla.api.Manufacturer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Job monitor.
 */
@Slf4j
@Service
public class JobMonitor {

    private final Map<Manufacturer, Histogram> fetchedEntities = new HashMap<>();
    private final Map<Manufacturer, Histogram> errorsDuringJobExecution = new HashMap<>();

    public JobMonitor(CollectorRegistry registry) {
        Arrays.stream(Manufacturer.values())
                .forEach(manufacturer -> {
                    var entityHistogram = Histogram.build(Metrics.ENTITIES_FETCHED_PREFIX + manufacturer.name().toLowerCase(),
                                    "Number of entities fetched from " + manufacturer.name())
                            .register(registry);
                    fetchedEntities.put(manufacturer, entityHistogram);

                    var errorHistogram = Histogram.build(Metrics.ERRORS_DURING_JOB_EXECUTION_PREFIX + manufacturer.name().toLowerCase(),
                                    "Number of errors during job execution for " + manufacturer.name())
                            .register(registry);
                    errorsDuringJobExecution.put(manufacturer, errorHistogram);
                });
    }

    /**
     * Monitor the number of entities fetched.
     *
     * @param nrOfEntities number of entities.
     * @param manufacturer manufacturer.
     */
    public void logNrOfEntitiesFetched(int nrOfEntities, Manufacturer manufacturer) {
        log.info("Fetched {} entities from {}", nrOfEntities, manufacturer);
        var histogram = fetchedEntities.get(manufacturer);
        if (histogram == null) {
            log.warn("No histogram found for manufacturer {}", manufacturer);
        } else {
            histogram.observe(nrOfEntities);
        }
    }

    /**
     * Logs an error that occurred during execution for a specific manufacturer.
     * If a histogram is found for the manufacturer, the error count is incremented by 1.
     * Otherwise, a warning message is logged.
     *
     * @param manufacturer the manufacturer of the sensor
     */
    public void logErrorDuringExecution(Manufacturer manufacturer) {
        log.info("Error during execution for {}", manufacturer);
        var histogram = errorsDuringJobExecution.get(manufacturer);
        if (histogram == null) {
            log.warn("No histogram found for manufacturer {}", manufacturer);
        } else {
            histogram.observe(1);
        }
    }

}
