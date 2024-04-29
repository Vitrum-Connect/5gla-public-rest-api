package de.app.fivegla.fiware.api;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provides methods for generating FIWARE IDs.
 * FIWARE IDs are used to uniquely identify entities in the FIWARE ecosystem.
 */
@Slf4j
public final class FiwareChecker {

    public static final int MAX_ID_LENGTH = 62;
    public static final int MAX_TYPE_LENGTH = 62;

    private FiwareChecker() {
        // private constructor to prevent instantiation
    }

    /**
     * Checks if the given ID is valid.
     *
     * @param id the ID to be checked
     * @throws FiwareIntegrationLayerException if the ID is too long
     */
    public static void checkId(String id) {
        if (id.length() > MAX_ID_LENGTH) {
            log.error("The id is too long. Please choose a shorter prefix.");
            log.debug("Checked ID: " + id);
            throw new FiwareIntegrationLayerException("The generated id is too long. Please choose a shorter value.");
        }
    }

    /**
     * Checks if the given type is valid.
     *
     * @param type the type to be checked
     * @throws FiwareIntegrationLayerException if the type is too long
     */
    public static void checkType(String type) {
        if (type.length() > MAX_TYPE_LENGTH) {
            log.error("The type is too long. Please choose a shorter prefix.");
            log.debug("Checked ID: " + type);
            throw new FiwareIntegrationLayerException("The generated type is too long. Please choose a shorter value.");
        }
    }

}
