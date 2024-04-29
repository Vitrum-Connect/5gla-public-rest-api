package de.app.fivegla.fiware.model.internal;

import de.app.fivegla.fiware.api.FiwareType;

/**
 * Represents an attribute.
 */
public record NumberAttribute(double value) implements Attribute {

    /**
     * Converts the attribute object to JSON format.
     *
     * @return The attribute object in JSON format.
     */
    @Override
    public String asJson() {
        return "{" +
                "  \"type\":\"" + FiwareType.NUMBER.getKey() + "\"," +
                "  \"value\":" + value +
                "}";
    }

}
