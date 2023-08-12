package de.app.fivegla.integration.sensoterra.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Domain model.
 */
@Getter
@Setter
public class Probe {
    private int id;
    private String serial;
    private String sku;
    @JsonProperty("dev_eui")
    private String devEui;
    private String name;
    private String state;
    private int interval;
    @JsonProperty("location_id")
    private int locationId;
    @JsonProperty("depth_id")
    private int depthId;
    @JsonProperty("soil_profile")
    private List<SoilProfile> soilProfile;
    private List<CustomProperty> customProperties;
    private double latitude;
    private double longitude;
    private double radius;
    @JsonProperty("loc_source")
    private String locSource;
    private String unit;
    @JsonProperty("customer_id")
    private int customerId;
    private int pos;
    private Status status;
    private String network;

}
