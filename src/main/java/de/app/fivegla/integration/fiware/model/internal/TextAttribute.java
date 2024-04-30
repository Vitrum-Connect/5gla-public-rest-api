package de.app.fivegla.integration.fiware.model.internal;

import de.app.fivegla.integration.fiware.api.FiwareType;

/**
 * Represents an attribute.
 */
public record TextAttribute(String value) implements Attribute {

    /**
     * Converts the attribute object to JSON format.
     *
     * @return The attribute object in JSON format.
     */
    @Override
    public String asJson() {
        return "{" +
                "  \"type\":\"" + FiwareType.TEXT.getKey() + "\"," +
                "  \"value\":\"" + value + "\"" +
                "}";
    }

}
