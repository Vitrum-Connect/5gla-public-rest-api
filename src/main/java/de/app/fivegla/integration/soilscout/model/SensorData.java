package de.app.fivegla.integration.soilscout.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The soil scout data.
 */
@Getter
@Setter
public class SensorData {

    @CsvDate("yyyy-MM-dd HH:mm:ss")
    @CsvBindByPosition(position = 0)
    private Date timestamp;
    @CsvBindByPosition(position = 3)
    private int device;
    @CsvBindByPosition(position = 10)
    private double moisture;
    @CsvBindByPosition(position = 11)
    private double temperature;
    @CsvIgnore
    private double conductivity;
    @CsvBindByPosition(position = 12)
    private double dielectricity;
    @CsvIgnore
    private int site;
    @CsvBindByPosition(position = 13)
    private double salinity;
    @JsonProperty("field_capacity")
    @CsvBindByPosition(position = 14)
    private double fieldCapacity;
    @JsonProperty("wilting_point")
    @CsvBindByPosition(position = 15)
    private double wiltingPoint;
    @JsonProperty("water_balance")
    @CsvBindByPosition(position = 16)
    private double waterBalance;

}