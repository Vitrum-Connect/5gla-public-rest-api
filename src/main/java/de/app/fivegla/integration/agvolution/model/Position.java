package de.app.fivegla.integration.agvolution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    @JsonProperty("lon")
    private double longitude;
    @JsonProperty("lat")
    private double latitude;
}
