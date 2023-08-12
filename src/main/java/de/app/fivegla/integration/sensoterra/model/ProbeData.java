package de.app.fivegla.integration.sensoterra.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Domain model.
 */
@Getter
@Setter
public class ProbeData {
    private Instant timestamp;
    private int height;
    private String unit;
    private double value;
}
