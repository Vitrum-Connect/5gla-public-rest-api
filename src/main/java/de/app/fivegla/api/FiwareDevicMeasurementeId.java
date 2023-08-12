package de.app.fivegla.api;

import java.util.UUID;

/**
 * Generates FIWARE device measurement IDs.
 */
public class FiwareDevicMeasurementeId {

    /**
     * Creates a FIWARE device measurement ID.
     *
     * @param manufacturer The manufacturer.
     * @return The FIWARE device measurement ID.
     */
    public static String create(Manufacturer manufacturer) {
        return create(manufacturer, UUID.randomUUID().toString());
    }

    /**
     * Creates a FIWARE device measurement ID.
     *
     * @param manufacturer The manufacturer.
     * @param id           The ID.
     * @return The FIWARE device measurement ID.
     */
    public static String create(Manufacturer manufacturer, String id) {
        return manufacturer.getFiwareDeviceIdPrefix() + id;
    }


}
