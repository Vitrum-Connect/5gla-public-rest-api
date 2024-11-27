package de.app.fivegla.integration.fiware.model.api;

import de.app.fivegla.business.agricrop.GpsCoordinate;
import de.app.fivegla.integration.fiware.api.FiwareType;

import java.util.List;

/**
 * The FiwareEntity interface represents an entity in the Fiware system.
 * Implementing classes should provide a JSON representation of the entity.
 */
public interface FiwareEntity {

    /**
     * Converts the FiwareEntity object to JSON format.
     *
     * @return The FiwareEntity object in JSON format.
     */
    String asJson();

    /**
     * Converts the FiwareEntity object to JSON format for a smart model.
     *
     * @return The FiwareEntity object in JSON format for a smart model.
     */
    String asSmartModelJson();

    /**
     * Returns the JSON representation of the location coordinates.
     *
     * @param latitude  the latitude coordinate
     * @param longitude the longitude coordinate
     * @return the JSON representation of the location coordinates
     */
    default String locationAsJson(double latitude, double longitude) {
        if (latitude == 0.0 && longitude == 0.0) {
            return "{}";
        } else {
            return "{" +
                    "  \"type\":\"" + FiwareType.GEO_JSON.getKey() + "\"," +
                    "  \"value\": {" +
                    "    \"type\":\"Point\"," +
                    "    \"coordinates\": [" + longitude + "," + latitude + "]" +
                    "  }" +
                    "}";
        }
    }

    /**
     * Converts a list of GpsCoordinate objects to a JSON string representing a Polygon in GeoJSON format.
     *
     * @param coordinates the list of GpsCoordinate objects to be converted
     * @return the JSON string representing the Polygon in GeoJSON format
     */
    default String coordinatesAsJson(List<GpsCoordinate> coordinates) {
        if (coordinates.isEmpty()) {
            return "{}";
        } else {
            return "{" +
                    "  \"type\":\"" + FiwareType.GEO_JSON.getKey() + "\"," +
                    "  \"value\": {" +
                    "    \"type\":\"Polygon\"," +
                    "    \"coordinates\": [" + coordinates.stream().map(c -> "[" + c.getLatitude() + "," + c.getLongitude() + "]").reduce((a, b) -> a + "," + b).orElse("") + "]" +
                    "  }" +
                    "}";
        }
    }

    /**
     * Returns the ID of the FiwareEntity object.
     *
     * @return The ID of the FiwareEntity object.
     */
    String getId();

    /**
     * Returns the type of the FiwareEntity object.
     *
     * @return The type of the FiwareEntity object.
     */
    String getType();

    /**
     * Returns whether a smart model entity should be created.
     *
     * @return true if a smart model entity should be created, false otherwise
     */
    boolean shouldCreateSmartModelEntity();
}
