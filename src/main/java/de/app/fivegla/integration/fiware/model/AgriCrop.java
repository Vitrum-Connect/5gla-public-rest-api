package de.app.fivegla.integration.fiware.model;

import de.app.fivegla.api.ZoneOrDefaultValue;
import de.app.fivegla.business.agricrop.GpsCoordinate;
import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.model.api.Validatable;
import de.app.fivegla.integration.fiware.model.internal.Attribute;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Device position.
 */
public record AgriCrop(
        String id,
        String type,
        ZoneOrDefaultValue zone,
        Attribute dateCreated,
        List<GpsCoordinate> coordinates
) implements Validatable, FiwareEntity {

    @Override
    public String asJson() {
        validate();
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"zone\":\"" + zone.value() + "\"," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                "  \"coordinates\":" + coordinatesAsJson(coordinates) +
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }
}
