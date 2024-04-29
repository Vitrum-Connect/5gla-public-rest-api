package de.app.fivegla.fiware.model.internal;

/**
 * Represents an attribute.
 */
public record EmptyAttribute() implements Attribute {

    /**
     * Converts the attribute object to JSON format.
     *
     * @return The attribute object in JSON format.
     */
    @Override
    public String asJson() {
        return "{" +
                "}";
    }

}
