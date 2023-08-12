package de.app.fivegla.integration.sensoterra.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Domain model.
 */
@Getter
@Setter
public class Status {
    private String code;
    private String message;
    private Instant lastUpdate;
    private int lastReading;
    private String connectivity;
    private int signalStrength;
    private int snr;
    private int rssi;
    private String battery;
    private int distance;
}
