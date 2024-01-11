package de.app.fivegla.monitoring;

import de.app.fivegla.api.Manufacturer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
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
    private final Map<Manufacturer, Histogram> jobExecutionTimes = new HashMap<>();

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

                    var jobExecutionTimeHistogram = Histogram.build(Metrics.JOB_EXECUTION_TIME_PREFIX + manufacturer.name().toLowerCase(),
                                    "Time taken to execute job for " + manufacturer.name())
                            .register(registry);
                    jobExecutionTimes.put(manufacturer, jobExecutionTimeHistogram);
                });
    }

    /**
     * Monitor the number of entities fetched.
     *
     * @param manufacturer manufacturer.
     * @param nrOfEntities number of entities.
     */
    public void logNrOfEntitiesFetched(Manufacturer manufacturer, int nrOfEntities) {
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

    /**
     * Logs the execution time of a job for a specific manufacturer.
     *
     * @param manufacturer The manufacturer of the job.
     * @param time         The execution time of the job in milliseconds.
     */
    public void logJobExecutionTime(Manufacturer manufacturer, long time) {
        log.info("Job execution time for {} was {} ms", manufacturer, time);
        var histogram = jobExecutionTimes.get(manufacturer);
        if (histogram == null) {
            log.warn("No histogram found for manufacturer {}", manufacturer);
        } else {
            histogram.observe(time);
        }
    }

}
