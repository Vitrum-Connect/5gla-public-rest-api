package de.app.fivegla.integration.agvolution.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a value for a specific time in a time series.
 * <p>
 * This class is annotated with the Lombok's @Getter and @Setter annotations,
 * which generate getters and setters for the private fields automatically.
 * The class is also annotated with @Schema which can be used for generating
 * documentation for API endpoints using Swagger or other similar frameworks.
 * <p>
 * An instance of this class contains information about the time and value of
 * a specific data point in a time series. The time is represented as an
 * Instant object using the UTC timezone. The value is a Double representing
 * the numeric value associated with the time.
 * <p>
 * The time is formatted using the ISO 8601 standard date and time format,
 * with milliseconds precision, and the UTC timezone.
 * <p>
 * Example usage:
 * <p>
 * TimeSeriesValue value = new TimeSeriesValue();
 * value.setTime(Instant.now());
 * value.setValue(42.0);
 */
@Getter
@Setter
@Schema(description = "Represents a value for a specific time in a time series.")
public class TimeSeriesValue {

    @Schema(description = "The time of the value in the time series.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    private Instant time;

    @Schema(description = "The value of the value in the time series.")
    private Double value;
}
