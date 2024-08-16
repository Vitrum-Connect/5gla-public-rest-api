package de.app.fivegla.integration.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents the current weather data returned by the OpenWeather API.
 */
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {

    @JsonProperty("dt")
    private long dt;

    @JsonProperty("sunrise")
    private long sunrise;

    @JsonProperty("sunset")
    private long sunset;

    @JsonProperty("temp")
    private double temp;

    @JsonProperty("feels_like")
    private double feelsLike;

    @JsonProperty("pressure")
    private int pressure;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("dew_point")
    private double dewPoint;

    @JsonProperty("uvi")
    private double uvi;

    @JsonProperty("clouds")
    private int clouds;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind_speed")
    private double windSpeed;

    @JsonProperty("wind_deg")
    private int windDeg;

    @JsonProperty("wind_gust")
    private double windGust;

    @JsonProperty("rain")
    private Rain rain;

    @JsonProperty("snow")
    private Snow snow;

}
