package de.app.fivegla.fiware.model;

import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.internal.Attribute;
import org.apache.commons.lang3.StringUtils;

/**
 * Device position.
 */
public record DevicePosition(
        String id,
        String type,
        Attribute transactionId,
        Attribute deviceId,
        Attribute dateCreated,
        double latitude,
        double longitude
) implements Validatable, FiwareEntity {

    @Override
    public String asJson() {
        validate();
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"transactionId\":" + transactionId.asJson() + "," +
                "  \"deviceId\":" + deviceId.asJson() + "," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                "  \"location\":" + locationAsJson(latitude, longitude) +
                "}";
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the device position must not be null or blank.");
        }
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("The type of the device position must not be null or blank.");
        }
    }

}
