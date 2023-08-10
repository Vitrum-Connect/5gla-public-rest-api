package de.app.fivegla.integration.agvolution.response.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response object.
 */
@Getter
@Setter
public class DeviceTimeSeries {
    @JsonProperty("deviceTimeseries")
    private List<DeviceTimeSeriesEntry> deviceTimeSeriesEntries;
}
