package de.app.fivegla.api;

import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;

import java.util.UUID;

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
        return create(commonManufacturerConfiguration, UUID.randomUUID().toString());
    }

    /**
     * Creates a FIWARE device measurement ID.
     *
     * @param commonManufacturerConfiguration The manufacturer.
     * @param id                              The ID.
     * @return The FIWARE device measurement ID.
     */
    public static String create(CommonManufacturerConfiguration commonManufacturerConfiguration, String id) {
        return commonManufacturerConfiguration.fiwareDeviceMeasurementIdPrefix() + id;
    }


}
