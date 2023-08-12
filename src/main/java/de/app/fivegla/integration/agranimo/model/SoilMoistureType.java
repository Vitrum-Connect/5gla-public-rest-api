package de.app.fivegla.integration.agranimo.model;

import lombok.Getter;

/**
 * Soil moisture type.
 */
@Getter
public enum SoilMoistureType {
    WATER_CONTENT("WaterContent"), WATER_HEIGHT("WaterHeight"), WATER_VOLUMETRIC("WaterVolumetric");

    private final String key;

    SoilMoistureType(String key) {
        this.key = key;
    }
}
