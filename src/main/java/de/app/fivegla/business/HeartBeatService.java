package de.app.fivegla.business;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The HeartBeatService class provides functionality to register heartbeats.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HeartBeatService {

    private final MeterRegistry meterRegistry;
    private final ConcurrentHashMap<String, AtomicLong> lastHeartbeatTimestamps = new ConcurrentHashMap<>();

    /**
     * Register a heartbeat using Micrometer.
     */
    public void registerHeartBeat(String id) {
        log.info("Registering heartbeat using Micrometer for sensor id: {}", id);
        var counter = meterRegistry.counter("heartbeat", "id", id);
        counter.increment();

        lastHeartbeatTimestamps.computeIfAbsent(id, key -> {
            AtomicLong timestamp = new AtomicLong();
            Gauge.builder("heartbeat.lastTimestamp", timestamp, AtomicLong::get)
                    .description("The timestamp of the last heartbeat for sensor id: " + id)
                    .tags("sensorId", id)
                    .register(meterRegistry);
            return timestamp;
        }).set(System.currentTimeMillis());
    }

}
