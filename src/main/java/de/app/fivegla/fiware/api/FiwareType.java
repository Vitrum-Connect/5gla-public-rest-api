package de.app.fivegla.fiware.api;

import lombok.Getter;

/**
 * This enumeration represents the types supported by Fiware.
 * <p>
 * Each FiwareType has a unique key which represents its value. The supported types are:
 * - GEO_POINT: Represents a geographical point.
 * - DATE_TIME: Represents a date and time.
 */
@Getter
public enum FiwareType {
    GEO_JSON("geo:json"),
    DATE_TIME("DateTime"),
    TEXT("Text"),
    NUMBER("Number"),
    BOOLEAN("Boolean"),
    STRUCTURED_VALUE("StructuredValue"),
    NONE("None");

    private final String key;

    FiwareType(String key) {
        this.key = key;
    }
}
