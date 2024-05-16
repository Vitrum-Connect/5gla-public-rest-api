package de.app.fivegla.integration.fiware.api;

import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * This class provides methods for generating FIWARE IDs.
 * FIWARE IDs are used to uniquely identify entities in the FIWARE ecosystem.
 */
@Slf4j
public final class FiwareEntityChecker {

    public static final int MAX_ID_LENGTH = 62;
    public static final int MAX_TYPE_LENGTH = 62;
    public static final int MAX_GROUP_NAME_LENGTH = 50;

    private FiwareEntityChecker() {
        // private constructor to prevent instantiation
    }


    public static void check(FiwareEntity entity) {
        checkId(entity.getId());
        checkType(entity.getType());
    }

    /**
     * Checks if the given ID is valid.
     *
     * @param id the ID to be checked
     */
    private static void checkId(String id) {
        if (id.length() > MAX_ID_LENGTH) {
            log.error("The id is too long. Please choose a shorter prefix.");
            throw new IllegalArgumentException("The generated id is too long. Please choose a shorter value.");
        }
    }

    /**
     * Checks if the given type is valid.
     *
     * @param type the type to be checked
     */
    private static void checkType(String type) {
        if (type.length() > MAX_TYPE_LENGTH) {
            log.error("The type is too long. Please choose a shorter prefix.");
            throw new IllegalArgumentException("The generated type is too long. Please choose a shorter value.");
        }
    }

    /**
     * Checks if the given group name is valid.
     *
     * @param groupName the group name to be checked
     */
    public static void checkGroupName(String groupName) {
        if (groupName.length() > MAX_GROUP_NAME_LENGTH) {
            log.error("The group name is too long. Your are not able to use more than 50 characters..");
            throw new IllegalArgumentException("The generated group name is too long. Please choose a shorter value.");
        }
        if (!groupName.matches("^[a-zA-Z0-9_]*$")) {
            log.error("The group name contains invalid characters. Only alphanumeric and underscore are allowed.");
            throw new IllegalArgumentException("The generated group name contains invalid characters. Only alphanumeric and underscore are allowed.");
        }
    }
}
