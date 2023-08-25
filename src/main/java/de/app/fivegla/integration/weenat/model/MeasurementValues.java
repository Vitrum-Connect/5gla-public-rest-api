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
    private String temperature;

    @JsonProperty("U")
    private String relativeHumidity;

    @JsonProperty("RR")
    private String cumulativeRainfall;

    @JsonProperty("FF")
    private String windSpeed;

    @JsonProperty("FXY")
    private String windGustSpeed;

    @JsonProperty("T_15")
    private String soilTemperature15;

    @JsonProperty("T_30")
    private String soilTemperature30;

    @JsonProperty("T_60")
    private String soilTemperature60;

    @JsonProperty("WHYD_15")
    private String soilWaterPotential15;

    @JsonProperty("WHYD_30")
    private String soilWaterPotential30;

    @JsonProperty("WHYD_60")
    private String soilWaterPotential60;

    @JsonProperty("T_DRY")
    private String dryTemperature;

    @JsonProperty("T_WET")
    private String wetTemperature;

    @JsonProperty("LW_DRY")
    private String leafWetnessDuration;

    @JsonProperty("LW_V")
    private String leafWetnessVoltage;

    @JsonProperty("T_SOIL")
    private String soilTemperature;

    @JsonProperty("SSI")
    private String solarIrridiance;

    @JsonProperty("SSI_MIN")
    private String minimumSolarIrridiance;

    @JsonProperty("SSI_MAX")
    private String maximumSolarIrridiance;

    @JsonProperty("PPFD")
    private String photosyntheticallyActiveRadiation;

    @JsonProperty("PPFD_MIN")
    private String minimumPhotosyntheticallyActiveRadiation;

    @JsonProperty("PPFD_MAX")
    private String maximumPhotosyntheticallyActiveRadiation;

    @JsonProperty("T_DEW")
    private String dewPoint;

    @JsonProperty("ETP")
    private String potentialEvapotranspiration;
}
