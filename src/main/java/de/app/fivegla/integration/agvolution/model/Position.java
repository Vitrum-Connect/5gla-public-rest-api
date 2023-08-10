package de.app.fivegla.integration.agvolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    @JsonProperty("lon")
    private double longitude;
    @JsonProperty("lat")
    private double latitude;
}
