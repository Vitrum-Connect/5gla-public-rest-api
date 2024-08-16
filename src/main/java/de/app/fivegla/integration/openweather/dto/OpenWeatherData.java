package de.app.fivegla.integration.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents the data returned by the OpenWeather API.
 */
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherData {

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("timezone_offset")
    private int timezoneOffset;

    @JsonProperty("current")
    private Current current;

}
