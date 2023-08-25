package de.app.fivegla.config;

import de.app.fivegla.config.manufacturer.*;


/**
 * Represents the configuration for a manufacturer.
 */
public record ManufacturerConfiguration(
        Farm21Configuration farm21,
        SoilscoutConfiguration soilscout,
        AgranimoConfiguration agranimo,
        AgvolutionConfiguration agvolution,
        SensoterraConfiguration sensoterra,
        MicasenseConfiguration micasense,
        SentekConfiguration sentek,
        WeenatConfiguration weenat) {
}