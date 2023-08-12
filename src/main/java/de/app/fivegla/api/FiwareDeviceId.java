package de.app.fivegla.api;

import java.util.UUID;

/**
 * Generates FIWARE device IDs.
 */
public class FiwareDeviceId {

    /**
     * Creates a FIWARE device ID.
     *
     * @param manufacturer The manufacturer.
     * @return The FIWARE device ID.
     */
    public static String create(Manufacturer manufacturer) {
        return create(manufacturer, UUID.randomUUID().toString());
    }

    /**
     * Creates a FIWARE device ID.
     *
     * @param manufacturer The manufacturer.
     * @param id           The ID.
     * @return The FIWARE device ID.
     */
    public static String create(Manufacturer manufacturer, String id) {
        return manufacturer.getFiwareDeviceIdPrefix() + id;
    }


}
