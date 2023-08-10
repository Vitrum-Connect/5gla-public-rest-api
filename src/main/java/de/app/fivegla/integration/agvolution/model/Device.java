package de.app.fivegla.integration.agvolution.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a device.
 */
@Getter
@Setter
public class Device {
    private String id;
    private Position position;
    private Instant latestSignal;
}
