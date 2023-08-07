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
    private final Map<Manufacturer, Counter> counters = new HashMap<>();

    public JobMonitor(CollectorRegistry registry) {
        Arrays.stream(Manufacturer.values())
                .forEach(manufacturer -> {
                    var counter = Counter.build(ENTITY_COUNTER_KEY_PREFIX + manufacturer.name().toLowerCase(),
                                    "Number of entities fetched from " + manufacturer.name())
                            .register(registry);
                    counters.put(manufacturer, counter);
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


}
