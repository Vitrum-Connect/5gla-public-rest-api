package de.app.fivegla.integration.fiware.model;

import de.app.fivegla.business.agricrop.GpsCoordinate;
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
        Attribute dateCreated,
        List<GpsCoordinate> coordinates
) implements Validatable, FiwareEntity {

    @Override
    public String asJson() {
        validate();
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                //"  \"location\":" + locationAsJson(latitude, longitude) +
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
