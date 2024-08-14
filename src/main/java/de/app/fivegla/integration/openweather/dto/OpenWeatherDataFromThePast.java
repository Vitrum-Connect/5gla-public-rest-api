package de.app.fivegla.integration.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

/**
 * This class represents the data returned by the OpenWeather API.
 */
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDataFromThePast {

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("timezone_offset")
    private int timezoneOffset;

    @JsonProperty("current")
    private Data data;

    @JsonProperty("rain.1h")
    private double rainWithinOneHour;

    @JsonProperty("snow.1h")
    private double snowWithinOneHour;

}
