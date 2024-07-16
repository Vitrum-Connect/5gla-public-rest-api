package de.app.fivegla.integration.fiware.model;

import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.model.api.Validatable;
import de.app.fivegla.integration.fiware.model.internal.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Device measurement.
 */
@Slf4j
public record DroneDeviceMeasurement(
        String id,
        String type,
        Attribute group,
        Attribute oid,
        Attribute droneId,
        Attribute transactionId,
        Attribute imageChannel,
        Attribute base64encodedImage,
        Attribute imagePath,
        double latitude,
        double longitude
) implements FiwareEntity, Validatable {

    @Override
    public String asJson() {
        validate();
        var json = "{" +
                "  \"id\":\"" + id.trim() + "\"," +
                "  \"type\":\"" + type.trim() + "\"," +
                "  \"customGroup\":" + group.asJson().trim() + "," +
                "  \"oid\":" + oid.asJson().trim() + "," +
                "  \"droneId\":" + droneId.asJson().trim() + "," +
                "  \"transactionId\":" + transactionId.asJson().trim() + "," +
                "  \"imageChannel\":" + imageChannel.asJson().trim() + "," +
                "  \"base64encodedImage\":" + base64encodedImage.asJson().trim() + "," +
                "  \"imagePath\":" + imagePath.asJson().trim() + "," +
                "  \"location\":" + locationAsJson(latitude, longitude).trim() +
                "}";
        log.debug("{} as JSON: {}", this.getClass().getSimpleName(), json);
        return json;
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the device measurement must not be null or blank.");
        }
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("The type of the device measurement must not be null or blank.");
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }
}
