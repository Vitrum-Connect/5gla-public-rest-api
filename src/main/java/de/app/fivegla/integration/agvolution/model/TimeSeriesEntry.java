package de.app.fivegla.integration.agvolution.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents an entry in a time series.
 * The entry consists of a key, unit, aggregate, and a list of time series values.
 */
@Getter
@Setter
@Schema(description = "Represents an entry in a time series.")
public class TimeSeriesEntry {

    @Schema(description = "The key of the entry.")
    private String key;

    @Schema(description = "The unit of the entry.")
    private String unit;

    @Schema(description = "The aggregate of the entry.")
    private String aggregate;

    @Schema(description = "The values of the entry.")
    private List<TimeSeriesValue> values;
}
