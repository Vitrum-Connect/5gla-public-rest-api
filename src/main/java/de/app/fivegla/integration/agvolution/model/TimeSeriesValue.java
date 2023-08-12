package de.app.fivegla.integration.agvolution.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class TimeSeriesValue {
    private Instant time;
    private Double value;
}
