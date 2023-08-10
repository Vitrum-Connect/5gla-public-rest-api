package de.app.fivegla.integration.agvolution.response.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.app.fivegla.integration.agvolution.model.SeriesEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response object.
 */
@Getter
@Setter
public class DeviceTimeSeriesEntry {
    @JsonProperty("series")
    private List<SeriesEntry> seriesEntries;
}
