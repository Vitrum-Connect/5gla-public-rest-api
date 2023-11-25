package de.app.fivegla.api;

import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.api.FiwareIdGenerator;

/**
 * Generates FIWARE device measurement IDs.
 */
public class FiwareDevicMeasurementeId {

    /**
     * Creates a FIWARE device measurement ID.
     *
     * @param commonManufacturerConfiguration The manufacturer.
     * @return The FIWARE device measurement ID.
     */
    public static String create(CommonManufacturerConfiguration commonManufacturerConfiguration) {
        return create(commonManufacturerConfiguration, "");
    }

    /**
     * Creates a FIWARE device measurement ID.
     *
     * @param commonManufacturerConfiguration The manufacturer.
     * @param id                              The ID.
     * @return The FIWARE device measurement ID.
     */
    public static String create(CommonManufacturerConfiguration commonManufacturerConfiguration, String id) {
        return FiwareIdGenerator.id(commonManufacturerConfiguration.fiwareDeviceIdPrefix() + id);
    }


}
