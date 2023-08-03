package de.app.fivegla.integration.agranimo.domain;

import lombok.Getter;

/**
 * Soil moisture type.
 */
public enum SoilMoistureType {
    WATER_CONTENT("WaterContent"), WATER_HEIGHT("WaterHeight"), WATER_VOLUMETRIC("WaterVolumetric");

    @Getter
    private final String key;

    SoilMoistureType(String key) {
        this.key = key;
    }
}
