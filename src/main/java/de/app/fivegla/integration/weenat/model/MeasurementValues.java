package de.app.fivegla.integration.weenat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * The MeasurementEntry class represents a single measurement entry with various weather-related properties.
 * Each property is annotated with the @JsonProperty annotation to specify the JSON key mapping.
 * The class also includes the getter and setter methods for each property.
 */
@Getter
@Setter
public class MeasurementValues {

    @JsonProperty("T")
    private Double temperature;

    @JsonProperty("U")
    private Double relativeHumidity;

    @JsonProperty("RR")
    private Double cumulativeRainfall;

    @JsonProperty("FF")
    private Double windSpeed;

    @JsonProperty("FXY")
    private Double windGustSpeed;

    @JsonProperty("T_15")
    private Double soilTemperature15;

    @JsonProperty("T_30")
    private Double soilTemperature30;

    @JsonProperty("T_60")
    private Double soilTemperature60;

    @JsonProperty("WHYD_15")
    private Double soilWaterPotential15;

    @JsonProperty("WHYD_30")
    private Double soilWaterPotential30;

    @JsonProperty("WHYD_60")
    private Double soilWaterPotential60;

    @JsonProperty("T_DRY")
    private Double dryTemperature;

    @JsonProperty("T_WET")
    private Double wetTemperature;

    @JsonProperty("LW_DRY")
    private Double leafWetnessDuration;

    @JsonProperty("LW_V")
    private Double leafWetnessVoltage;

    @JsonProperty("T_SOIL")
    private Double soilTemperature;

    @JsonProperty("SSI")
    private Double solarIrridiance;

    @JsonProperty("SSI_MIN")
    private Double minimumSolarIrridiance;

    @JsonProperty("SSI_MAX")
    private Double maximumSolarIrridiance;

    @JsonProperty("PPFD")
    private Double photosyntheticallyActiveRadiation;

    @JsonProperty("PPFD_MIN")
    private Double minimumPhotosyntheticallyActiveRadiation;

    @JsonProperty("PPFD_MAX")
    private Double maximumPhotosyntheticallyActiveRadiation;

    @JsonProperty("T_DEW")
    private Double dewPoint;

    @JsonProperty("ETP")
    private Double potentialEvapotranspiration;
}
