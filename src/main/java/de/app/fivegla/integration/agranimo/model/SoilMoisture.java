package de.app.fivegla.integration.agranimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * The SoilMoisture class represents the soil moisture data obtained by a device.
 * It holds information such as device ID, timestamp, creation timestamp, and soil moisture readings from four different sensors.
 */
@Getter
@Setter
public class SoilMoisture {

    /**
     * The deviceId variable represents the unique identifier of a device.
     * It is a string value used to uniquely identify a specific device.
     * The device ID is typically assigned by the manufacturer or system administrator.
     */
    private String deviceId;

    /**
     * The tms variable represents the timestamp of the soil moisture data.
     * It is an Instant object that stores an instant in time with nanosecond precision.
     * The timestamp indicates when the soil moisture reading was recorded.
     *
     * @see SoilMoisture
     */
    @JsonProperty("TMS")
    private Instant tms;

    /**
     * The createdAt variable represents the timestamp when the instance of SoilMoisture object was created.
     * It is an Instant object that stores an instant in time with nanosecond precision.
     * The createdAt timestamp indicates when the SoilMoisture object was instantiated.
     *
     * @see SoilMoisture
     */
    private Instant createdAt;

    /**
     * The smo1 variable represents the soil moisture reading from the first sensor.
     * It is a double value that indicates the level of moisture in the soil.
     * The value is obtained from the SoilMoisture object and can be accessed and modified using getter and setter methods.
     */
    private double smo1;

    /**
     * The smo2 variable represents the soil moisture reading from the second sensor.
     * It is a double value that indicates the level of moisture in the soil
     * as measured by the second sensor in the SoilMoisture object.
     * This variable is accessible through getter and setter methods.
     *
     * @see SoilMoisture#getSmo2()
     * @see SoilMoisture#setSmo2(double)
     */
    private double smo2;

    /**
     * The smo3 variable represents the soil moisture reading from the third sensor.
     * It is a double value that indicates the level of moisture in the soil
     * as measured by the third sensor in the SoilMoisture object.
     * This variable is accessible through getter and setter methods.
     *
     * @see SoilMoisture#getSmo3()
     * @see SoilMoisture#setSmo3(double)
     */
    private double smo3;

    /**
     * The `smo4` variable represents the soil moisture reading from the fourth sensor.
     * It is a double value that indicates the level of moisture in the soil
     * as measured by the fourth sensor in the SoilMoisture object.
     *
     * @see SoilMoisture#getSmo4()
     * @see SoilMoisture#setSmo4(double)
     */
    private double smo4;

}
