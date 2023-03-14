package de.app.fivegla.integration.soilscout.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The soil scout data.
 */
@Getter
@Setter
public class SoilScoutSensorData {

    private Date timestamp;
    private int device;
    private double temperature;
    private double moisture;
    private double conductivity;
    private double dielectricity;
    private int site;
    private double salinity;
    @SerializedName("field_capacity")
    private double fieldCapacity;
    @SerializedName("wilting_point")
    private double wiltingPoint;
    @SerializedName("water_balance")
    private double waterBalance;

}