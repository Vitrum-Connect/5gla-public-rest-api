package de.app.fivegla.integration.agvolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a device timeseries entry.
 */
@Getter
@Setter
public class SeriesEntry {
    @JsonProperty("device")
    private String deviceId;
    @JsonProperty("lon")
    private double longitude;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("timeseries")
    private List<TimeSeriesEntry> timeSeriesEntries;
}
