package de.app.fivegla.integration.soilscout.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Model.
 */
@Getter
@Setter
public class Sensor {

    private int id;
    @JsonProperty("serial_number")
    private int serialNumber;
    private String name;
    private String imei;
    @JsonProperty("device_type")
    private String deviceType;
    @JsonProperty("voltage_external")
    private double voltageExternal;
    @JsonProperty("voltage_battery")
    private double voltageBattery;
    private Location location;
    private int site;
    @JsonProperty("last_seen")
    private Date lastSeen;
    @JsonProperty("device_status")
    private String deviceStatus;
    @JsonProperty("antenna_type")
    private String antennaType;
    @JsonProperty("antenna_orientation")
    private int antennaOrientation;
    @JsonProperty("rain_mm_per_pulse")
    private double rainMmPerPulse;
    @JsonProperty("has_battery")
    private String hasBattery;
}
