package de.app.fivegla.integration.weenat.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class Measurement {
    private final Instant timestamp;
    private final MeasurementValues measurementValues;
}
