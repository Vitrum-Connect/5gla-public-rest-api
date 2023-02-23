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
public class SoilScoutSensor {
    int id;

    @JsonProperty("serial_number")
    int serialNumber;

    String name;

    String imei;

    @JsonProperty("device_type")
    String deviceType;

    @JsonProperty("voltage_external")
    double voltageExternal;

    @JsonProperty("voltage_battery")
    double voltageBattery;

    SoilScoutLocation location;

    int site;

    @JsonProperty("last_seen")
    Date lastSeen;

    @JsonProperty("device_status")
    String deviceStatus;

    @JsonProperty("antenna_type")
    String antennaType;

    @JsonProperty("antenna_orientation")
    int antennaOrientation;

    @JsonProperty("rain_mm_per_pulse")
    double rainMmPerPulse;

    @JsonProperty("has_battery")
    String hasBattery;
}
