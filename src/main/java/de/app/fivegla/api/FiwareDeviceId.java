package de.app.fivegla.api;

import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;

import java.util.UUID;

/**
 * Generates FIWARE device IDs.
 */
public class FiwareDeviceId {

    /**
     * Creates a FIWARE device ID.
     *
     * @param manufacturerConfiguration The manufacturer.
     * @return The FIWARE device ID.
     */
    public static String create(CommonManufacturerConfiguration manufacturerConfiguration) {
        return create(manufacturerConfiguration, UUID.randomUUID().toString());
    }

    /**
     * Creates a FIWARE device ID.
     *
     * @param commonManufacturerConfiguration The manufacturer.
     * @param id                              The ID.
     * @return The FIWARE device ID.
     */
    public static String create(CommonManufacturerConfiguration commonManufacturerConfiguration, String id) {
        return commonManufacturerConfiguration.fiwareDeviceIdPrefix() + id;
    }


}
