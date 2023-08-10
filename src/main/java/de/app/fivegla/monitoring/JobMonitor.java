package de.app.fivegla.monitoring;

import de.app.fivegla.api.Manufacturer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
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

    private static final String ENTITY_COUNTER_KEY_PREFIX = "entities_fetched_";
    private static final String NR_OF_RUNS = "number_of_runs_";
    private final Map<Manufacturer, Counter> counters = new HashMap<>();
    private final Map<Manufacturer, Counter> runs = new HashMap<>();

    public JobMonitor(CollectorRegistry registry) {
        Arrays.stream(Manufacturer.values())
                .forEach(manufacturer -> {
                    var entityCounter = Counter.build(ENTITY_COUNTER_KEY_PREFIX + manufacturer.name().toLowerCase(),
                                    "Number of entities fetched from " + manufacturer.name())
                            .register(registry);
                    counters.put(manufacturer, entityCounter);

                    var runCounter = Counter.build(NR_OF_RUNS + manufacturer.name().toLowerCase(),
                                    "Number of runs for " + manufacturer.name())
                            .register(registry);
                    runs.put(manufacturer, runCounter);
                });
    }

    /**
     * Monitor number of entities fetched.
     *
     * @param nrOfEntities number of entities.
     * @param manufacturer manufacturer.
     */
    public void nrOfEntitiesFetched(int nrOfEntities, Manufacturer manufacturer) {
        log.info("Fetched {} entities from {}", nrOfEntities, manufacturer);
        var counter = counters.get(manufacturer);
        if (counter == null) {
            log.warn("No counter found for manufacturer {}", manufacturer);
        } else {
            counter.inc(nrOfEntities);
        }
    }

    /**
     * Increment number of runs.
     *
     * @param manufacturer manufacturer.
     */
    public void incNrOfRuns(Manufacturer manufacturer) {
        log.info("Incrementing number of runs for {}", manufacturer);
        var counter = runs.get(manufacturer);
        if (counter == null) {
            log.warn("No counter found for manufacturer {}", manufacturer);
        } else {
            counter.inc();
        }
    }

}
