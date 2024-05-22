package de.app.fivegla.integration.fiware.model;

import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.model.api.Validatable;
import de.app.fivegla.integration.fiware.model.internal.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Device position.
 */
@Slf4j
public record DevicePosition(
        String id,
        String type,
        Attribute group,
        Attribute transactionId,
        Attribute deviceId,
        Attribute dateCreated,
        double latitude,
        double longitude
) implements Validatable, FiwareEntity {

    @Override
    public String asJson() {
        validate();
        var json = "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"customGroup\":" + group.asJson() + "," +
                "  \"transactionId\":" + transactionId.asJson() + "," +
                "  \"deviceId\":" + deviceId.asJson() + "," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                "  \"location\":" + locationAsJson(latitude, longitude) +
                "}";
        log.debug("{} as JSON: {}", this.getClass().getSimpleName(), json);
        return json;
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }
}
