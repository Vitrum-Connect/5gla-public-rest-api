package de.app.fivegla.integration.fiware.model;

import de.app.fivegla.api.ZoneOrDefaultValue;
import de.app.fivegla.business.agricrop.GpsCoordinate;
import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.model.api.Validatable;
import de.app.fivegla.integration.fiware.model.internal.Attribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;

import java.util.List;

/**
 * Device position.
 */
public record AgriCrop(
        String id,
        String type,
        Attribute group,
        Attribute dateCreated,
        List<GpsCoordinate> coordinates
) implements Validatable, FiwareEntity {

    @Override
    public String asJson() {
        validate();
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"group\":\"" + group.asJson() + "\"," +
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
