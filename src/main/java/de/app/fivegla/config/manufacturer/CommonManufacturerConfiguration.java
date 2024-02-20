package de.app.fivegla.config.manufacturer;


import de.app.fivegla.api.Manufacturer;

/**
 * Represents a common configuration interface for a manufacturer.
 */
public interface CommonManufacturerConfiguration {

    /**
     * Get the key for the manufacturer.
     *
     * @return the key
     */
    default String getKey() {
        return "urn:5gla:manufacturer:" + manufacturerName().toLowerCase();
    }

    /**
     * Get the name of the manufacturer.
     *
     * @return the name of the manufacturer
     */
    String manufacturerName();

    /**
     * Get the FIWARE device ID prefix.
     *
     * @return the FIWARE device ID prefix as a String
     */
    String fiwareDeviceIdPrefix();

    /**
     * Get the manufacturer of the device.
     *
     * @return the manufacturer as an instance of the Manufacturer enum
     */
    Manufacturer manufacturer();
}