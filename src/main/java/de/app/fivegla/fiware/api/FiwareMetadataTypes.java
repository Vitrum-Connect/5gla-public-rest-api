package de.app.fivegla.fiware.api;

import lombok.Getter;

/**
 * Enum representing the metadata types used in Fiware.
 * Each metadata type is associated with a key.
 */
@Getter
public enum FiwareMetadataTypes {
    CONTROLLED_PROPERTY("controlledProperty");
    private final String key;

    FiwareMetadataTypes(String key) {
        this.key = key;
    }
}
