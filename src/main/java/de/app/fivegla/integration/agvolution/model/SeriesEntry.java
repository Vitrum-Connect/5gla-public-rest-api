package de.app.fivegla.integration.agvolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a device timeseries entry.
 */
@Getter
@Setter
@Schema(description = "Represents a device time-series entry.")
public class SeriesEntry {

    @Schema(description = "The device ID.")
    @JsonProperty("device")
    private String deviceId;

    @Schema(description = "The device type.")
    @JsonProperty("lon")
    private double longitude;

    @Schema(description = "The device type.")
    @JsonProperty("lat")
    private double latitude;

    @Schema(description = "The device type.")
    @JsonProperty("timeseries")
    private List<TimeSeriesEntry> timeSeriesEntries;
}
