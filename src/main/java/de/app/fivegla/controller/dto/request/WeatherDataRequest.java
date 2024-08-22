package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents a request to add a weather data.
 */
@Getter
@Setter
@Schema(description = "Represents a request to add a weather data.")
public class WeatherDataRequest {

    @Schema(description = "The group id.")
    private String groupId;

    @NotNull
    @Schema(description = "The date the precipitation was created.")
    private Date dateCreated;

    @Schema(description = "The latitude.")
    private double latitude;

    @Schema(description = "The longitude.")
    private double longitude;

    @Schema(description = "The temperature.")
    private double temp;

    @Schema(description = "The humidity.")
    private double humidity;

    @Schema(description = "The precipitation.")
    private double precipitation;

    @Schema(description = "The air pressure.")
    private double airPressure;

    @Schema(description = "The UV index.")
    private double uvIndex;

    @Schema(description = "The solar radiation.")
    private double solarRadiation;

    @Schema(description = "The visibility.")
    private double visibility;

    @Schema(description = "The dew point.")
    private double dewPoint;

    @Schema(description = "The feels like.")
    private double feelsLike;

    @Schema(description = "The heat index.")
    private double heatIndex;
}
