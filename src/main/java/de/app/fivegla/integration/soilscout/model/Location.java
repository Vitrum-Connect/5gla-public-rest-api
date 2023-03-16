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

    double latitude;

    double longitude;

    double height;

    @JsonProperty("soil_type")
    String soilType;

    @JsonProperty("soil_density")
    double soilDensity;

    @JsonProperty("field_capacity")
    double fieldCapacity;

    @JsonProperty("wilting_point")
    double wiltingPoint;

    @JsonProperty("irrigation_threshold")
    double irrigationThreshold;

    @JsonProperty("site_id")
    int siteId;

}
