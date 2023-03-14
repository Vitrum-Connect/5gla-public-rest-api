package de.app.fivegla.integration.soilscout.cache;

import de.app.fivegla.integration.soilscout.model.SoilScoutSensor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Sensor cache.
 */
@Slf4j
@Component
public class SensorCache {

    private static final Map<Integer, SensorCacheEntry> CACHE = new HashMap<>();

    /**
     * Get the sensor from the cache.
     *
     * @param sensorId the sensor id
     * @return the sensor
     */
    public Optional<SoilScoutSensor> get(int sensorId) {
        if (CACHE.containsKey(sensorId)) {
            var sensorCacheEntry = CACHE.get(sensorId);
            if (!sensorCacheEntry.isValid()) {
                CACHE.remove(sensorId);
            } else {
                return Optional.of(sensorCacheEntry.getSoilScoutSensor());
            }
        }
        return Optional.empty();
    }

    public void put(SoilScoutSensor sensor) {
        CACHE.put(sensor.getId(), new SensorCacheEntry(sensor));
    }

    private static class SensorCacheEntry {
        private final Instant placedInCache;
        @Getter
        private final SoilScoutSensor soilScoutSensor;

        private SensorCacheEntry(SoilScoutSensor soilScoutSensor) {
            this.placedInCache = Instant.now();
            this.soilScoutSensor = soilScoutSensor;
        }

        private boolean isValid() {
            return placedInCache.plus(1, ChronoUnit.HOURS).isAfter(Instant.now());
        }
    }
}
