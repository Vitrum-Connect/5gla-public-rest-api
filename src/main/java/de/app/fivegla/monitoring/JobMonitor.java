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
@RequiredArgsConstructor
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
    public void nrOfEntitiesFetched(int nrOfEntities, Manufacturer manufacturer) {
        log.info("Fetched {} entities from {}", nrOfEntities, manufacturer);
        var histogram = fetchedEntities.get(manufacturer);
        if (histogram == null) {
            log.warn("No histogram found for manufacturer {}", manufacturer);
        } else {
            histogram.observe(nrOfEntities);
        }
    }

}
