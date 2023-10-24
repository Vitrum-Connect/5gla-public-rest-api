package de.app.fivegla.monitoring;

import de.app.fivegla.api.Manufacturer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * AOP monitor for fiware entities.
 */
@Slf4j
@Component
public class FiwareEntityMonitor {

    private final Map<Manufacturer, Counter> entitiesSavedOrUpdated = new HashMap<>();
    private final Map<Manufacturer, Counter> sensorsSavedOrUpdated = new HashMap<>();
    private final Map<Manufacturer, Counter> devicesRegistered = new HashMap<>();
    private final Counter entitiesSavedOrUpdatedAll;
    private final Counter sensorsSavedOrUpdatedAll;
    private final Counter devicesRegisteredAll;

    public FiwareEntityMonitor(CollectorRegistry registry) {
        Arrays.stream(Manufacturer.values())
                .forEach(manufacturer -> {
                    var entityCounter = Counter.build(Metrics.FIWARE_SAVED_ENTITIES_PREFIX + manufacturer.name().toLowerCase(),
                                    "Number of entities fetched from " + manufacturer.name())
                            .register(registry);
                    entitiesSavedOrUpdated.put(manufacturer, entityCounter);
                    var sensorCounter = Counter.build(Metrics.FIWARE_SAVED_SENSORS_PREFIX + manufacturer.name().toLowerCase(),
                                    "Number of entities fetched from " + manufacturer.name())
                            .register(registry);
                    sensorsSavedOrUpdated.put(manufacturer, sensorCounter);
                    var deviceCounter = Counter.build(Metrics.FIWARE_REGISTERED_DEVICES_PREFIX + manufacturer.name().toLowerCase(),
                                    "Number of devices registered from " + manufacturer.name())
                            .register(registry);
                    devicesRegistered.put(manufacturer, deviceCounter);
                });
        entitiesSavedOrUpdatedAll = Counter.build(Metrics.FIWARE_SAVED_ENTITIES,
                        "Number of entities fetched from all manufacturers")
                .register(registry);
        sensorsSavedOrUpdatedAll = Counter.build(Metrics.FIWARE_SAVED_SENSORS,
                        "Number of sensors fetched from all manufacturers")
                .register(registry);
        devicesRegisteredAll = Counter.build(Metrics.FIWARE_REGISTERED_DEVICES,
                        "Number of devices registered from all manufacturers")
                .register(registry);
    }

    /**
     * Monitor the number of entities saved.
     */
    public void entitiesSavedOrUpdated(Manufacturer manufacturer, int nrOfEntities) {
        log.info("Saved {} entities from {}", nrOfEntities, manufacturer);
        var counter = entitiesSavedOrUpdated.get(manufacturer);
        if (counter == null) {
            log.warn("No counter found for manufacturer {}", manufacturer);
        } else {
            counter.inc(nrOfEntities);
            entitiesSavedOrUpdatedAll.inc(nrOfEntities);
        }
    }

    /**
     * Monitor the number of entities saved.
     */
    public void entitiesSavedOrUpdated(Manufacturer manufacturer) {
        entitiesSavedOrUpdated(manufacturer, 1);
    }

    /**
     * Monitor the number of sensors saved.
     */
    public void sensorsSavedOrUpdated(Manufacturer manufacturer, int nrOfSensors) {
        log.info("Saved {} sensors from {}", nrOfSensors, manufacturer);
        var counter = sensorsSavedOrUpdated.get(manufacturer);
        if (counter == null) {
            log.warn("No counter found for manufacturer {}", manufacturer);
        } else {
            counter.inc(nrOfSensors);
            sensorsSavedOrUpdatedAll.inc(nrOfSensors);
        }
    }

    /**
     * Monitor the number of sensors saved.
     */
    public void sensorsSavedOrUpdated(Manufacturer manufacturer) {
        sensorsSavedOrUpdated(manufacturer, 1);
    }

    /**
     * Register a device for the given manufacturer and update the device counter.
     *
     * @param manufacturer the manufacturer of the device
     * @param nrOfDevices  the number of devices to register
     */
    public void deviceRegistered(Manufacturer manufacturer, int nrOfDevices) {
        log.info("Registered device for {}", manufacturer);
        var counter = devicesRegistered.get(manufacturer);
        if (counter == null) {
            log.warn("No counter found for manufacturer {}", manufacturer);
        } else {
            counter.inc();
            devicesRegisteredAll.inc(nrOfDevices);
        }
    }
}
