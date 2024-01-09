package de.app.fivegla.integration.soilscout.cache;

import de.app.fivegla.integration.soilscout.model.Sensor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SoilScoutSensorCache {

    private static final Map<Integer, SensorCacheEntry> CACHE = new HashMap<>();

    /**
     * Get the sensor from the cache.
     *
     * @param sensorId the sensor id
     * @return the sensor
     */
    public Optional<Sensor> get(int sensorId) {
        if (CACHE.containsKey(sensorId)) {
            var sensorCacheEntry = CACHE.get(sensorId);
            if (!sensorCacheEntry.isValid()) {
                CACHE.remove(sensorId);
            } else {
                return Optional.of(sensorCacheEntry.getSensor());
            }
        }
        return Optional.empty();
    }

    public void put(Sensor sensor) {
        CACHE.put(sensor.getId(), new SensorCacheEntry(sensor));
    }

    private static class SensorCacheEntry {
        private final Instant placedInCache;
        @Getter
        private final Sensor sensor;

        private SensorCacheEntry(Sensor sensor) {
            this.placedInCache = Instant.now();
            this.sensor = sensor;
        }

        private boolean isValid() {
            return placedInCache.plus(1, ChronoUnit.HOURS).isAfter(Instant.now());
        }
    }
}
