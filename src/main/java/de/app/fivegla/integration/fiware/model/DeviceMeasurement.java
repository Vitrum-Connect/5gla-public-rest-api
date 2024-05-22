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
public record DeviceMeasurement(
        String id,
        String type,
        Attribute group,
        Attribute name,
        Attribute controlledProperty,
        Attribute dateCreated,
        Attribute externalDataReference,
        double latitude,
        double longitude
) implements FiwareEntity, Validatable {

    @Override
    public String asJson() {
        validate();
        var json = "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"customGroup\":" + group.asJson() + "," +
                "  \"name\":" + name.asJson() + "," +
                "  \"controlledProperty\":" + controlledProperty.asJson() + "," +
                "  \"externalDataReference\":" + externalDataReference.asJson() + "," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                "  \"location\":" + locationAsJson(latitude, longitude) +
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
