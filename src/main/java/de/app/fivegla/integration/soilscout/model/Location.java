package de.app.fivegla.integration.soilscout.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Model.
 */
@Getter
@Setter
public class Location {

    private double latitude;
    private double longitude;
    private double height;
    @JsonProperty("soil_type")
    private String soilType;
    @JsonProperty("soil_density")
    private double soilDensity;
    @JsonProperty("field_capacity")
    private double fieldCapacity;
    @JsonProperty("wilting_point")
    private double wiltingPoint;
    @JsonProperty("irrigation_threshold")
    private double irrigationThreshold;
    @JsonProperty("site_id")
    private int siteId;

}
