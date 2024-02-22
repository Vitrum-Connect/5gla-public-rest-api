package de.app.fivegla.api;

import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.api.FiwareIdChecker;

/**
 * Generates FIWARE device IDs.
 */
public class FiwareIdGenerator {

    /**
     * Creates a FIWARE device ID.
     *
     * @param manufacturerConfiguration The manufacturer.
     * @return The FIWARE device ID.
     */
    public static String create(CommonManufacturerConfiguration manufacturerConfiguration) {
        return create(manufacturerConfiguration, "");
    }

    /**
     * Creates a FIWARE device ID.
     *
     * @param commonManufacturerConfiguration The manufacturer.
     * @param id                              The ID.
     * @return The FIWARE device ID.
     */
    public static String create(CommonManufacturerConfiguration commonManufacturerConfiguration, String id) {
        var fiwareId = commonManufacturerConfiguration.fiwareDeviceIdPrefix() + id;
        FiwareIdChecker.check(fiwareId);
        return fiwareId;
    }


}
