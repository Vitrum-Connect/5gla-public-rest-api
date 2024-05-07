package de.app.fivegla.integration.fiware.model;

import de.app.fivegla.api.ZoneOrDefaultValue;
import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.model.api.Validatable;
import de.app.fivegla.integration.fiware.model.internal.Attribute;
import org.apache.commons.lang3.StringUtils;

/**
 * Device measurement.
 */
public record DeviceMeasurement(
        String id,
        String type,
        ZoneOrDefaultValue zone,
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
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"zone\":\"" + zone.value() + "\"," +
                "  \"name\":" + name.asJson() + "," +
                "  \"controlledProperty\":" + controlledProperty.asJson() + "," +
                "  \"externalDataReference\":" + externalDataReference.asJson() + "," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                "  \"location\":" + locationAsJson(latitude, longitude) +
                "}";
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
