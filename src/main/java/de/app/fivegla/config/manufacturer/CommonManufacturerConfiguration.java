package de.app.fivegla.config.manufacturer;


import de.app.fivegla.api.Manufacturer;

/**
 * Represents a common configuration interface for a manufacturer.
 */
public interface CommonManufacturerConfiguration {

    /**
     * Get the FIWARE device ID prefix.
     *
     * @return the FIWARE device ID prefix as a String
     */
    String fiwarePrefix();

    /**
     * Get the manufacturer of the device.
     *
     * @return the manufacturer as an instance of the Manufacturer enum
     */
    Manufacturer manufacturer();

    /**
     * Checks if the configuration for the manufacturer is enabled.
     *
     * @return true if the configuration is enabled, false otherwise
     */
    boolean enabled();
}