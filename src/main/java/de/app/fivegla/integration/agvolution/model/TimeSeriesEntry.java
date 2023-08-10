package de.app.fivegla.integration.agvolution.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimeSeriesEntry {
    private String key;
    private String unit;
    private String aggregate;
    private List<TimeSeriesValue> values;
}
