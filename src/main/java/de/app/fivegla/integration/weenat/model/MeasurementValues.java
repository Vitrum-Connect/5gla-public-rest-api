package de.app.fivegla.integration.weenat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


/**
 * The MeasurementEntry class represents a single measurement entry with various weather-related properties.
 * Each property is annotated with the @JsonProperty annotation to specify the JSON key mapping.
 * The class also includes the getter and setter methods for each property.
 */
@Getter
@Setter
@Schema(description = "Represents a single measurement entry with various weather-related properties.")
public class MeasurementValues {

    @Schema(description = "The temperature of the measurement.")
    @JsonProperty("T")
    private Double temperature;

    @Schema(description = "The relative humidity of the measurement.")
    @JsonProperty("U")
    private Double relativeHumidity;

    @Schema(description = "The cumulative rainfall of the measurement.")
    @JsonProperty("RR")
    private Double cumulativeRainfall;

    @Schema(description = "The wind speed of the measurement.")
    @JsonProperty("FF")
    private Double windSpeed;

    @Schema(description = "The wind gust speed of the measurement.")
    @JsonProperty("FXY")
    private Double windGustSpeed;

    @Schema(description = "The soil temperature in 15 cm of the measurement.")
    @JsonProperty("T_15")
    private Double soilTemperature15;

    @Schema(description = "The soil temperature in 30 cm of the measurement.")
    @JsonProperty("T_30")
    private Double soilTemperature30;

    @Schema(description = "The soil temperature in 50 cm of the measurement.")
    @JsonProperty("T_60")
    private Double soilTemperature60;

    @Schema(description = "The soil water potential in 15 cm of the measurement.")
    @JsonProperty("WHYD_15")
    private Double soilWaterPotential15;

    @Schema(description = "The soil water potential in 30 cm of the measurement.")
    @JsonProperty("WHYD_30")
    private Double soilWaterPotential30;

    @Schema(description = "The soil water potential in 60 cm of the measurement.")
    @JsonProperty("WHYD_60")
    private Double soilWaterPotential60;

    @Schema(description = "The dry temperature of the measurement.")
    @JsonProperty("T_DRY")
    private Double dryTemperature;

    @Schema(description = "The wet temperature of the measurement.")
    @JsonProperty("T_WET")
    private Double wetTemperature;

    @Schema(description = "The leaf wetness duration of the measurement.")
    @JsonProperty("LW_DRY")
    private Double leafWetnessDuration;

    @Schema(description = "The leaf wetness voltage of the measurement.")
    @JsonProperty("LW_V")
    private Double leafWetnessVoltage;

    @Schema(description = "The soil temperature of the measurement.")
    @JsonProperty("T_SOIL")
    private Double soilTemperature;

    @Schema(description = "The solar irradiance of the measurement.")
    @JsonProperty("SSI")
    private Double solarIrradiance;

    @Schema(description = "The minimum solar irradiance  of the measurement.")
    @JsonProperty("SSI_MIN")
    private Double minSolarIrradiance;

    @Schema(description = "The maximum solar irradiance  of the measurement.")
    @JsonProperty("SSI_MAX")
    private Double maxSolarIrradiance;

    @Schema(description = "The photosynthetically active radiation of the measurement.")
    @JsonProperty("PPFD")
    private Double photosyntheticallyActiveRadiation;

    @Schema(description = "The minimum photosynthetically active radiation of the measurement.")
    @JsonProperty("PPFD_MIN")
    private Double minimumPhotosyntheticallyActiveRadiation;

    @Schema(description = "The maximum photosynthetically active radiation of the measurement.")
    @JsonProperty("PPFD_MAX")
    private Double maximumPhotosyntheticallyActiveRadiation;

    @Schema(description = "The dew point of the measurement.")
    @JsonProperty("T_DEW")
    private Double dewPoint;

    @Schema(description = "The potential evapotranspiration of the measurement.")
    @JsonProperty("ETP")
    private Double potentialEvapotranspiration;
}
