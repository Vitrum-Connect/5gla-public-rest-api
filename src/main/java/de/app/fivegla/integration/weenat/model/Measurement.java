package de.app.fivegla.integration.weenat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.app.fivegla.api.GlobalDefinitions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a value for a specific time in a time series.
 */
@Getter
@Setter
@Schema(description = "Represents a value for a specific time in a time series.")
public class Measurement {

    /**
     * Represents the timestamp of a value in a time series.
     * <p>
     * This variable is used to store the time at which a value was recorded in the time series.
     */
    @Schema(description = "The time of the value in the time series.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GlobalDefinitions.INSTANT_JSON_PATTERN, timezone = "UTC")
    private Instant timestamp;

    /**
     * The value of the value in the time series.
     */
    @Schema(description = "The value of the value in the time series.")
    private MeasurementValues measurementValues;
}
