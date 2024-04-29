package de.app.fivegla.fiware.model;

import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.internal.Attribute;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * A crop.
 */
public record AgriCrop(
        String id,
        String type,
        Attribute name,
        Attribute dateCreated,
        double[] coordinates
) implements Validatable {


    public String asJson() {
        validate();
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"name\":" + name.asJson() + "," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                "  \"location\":" + locationAsJson() +
                "}";
    }

    private String locationAsJson() {
        return "{" +
                "  \"type\":\"" + FiwareType.GEO_JSON.getKey() + "\"," +
                "  \"value\": {" +
                "    \"type\":\"Polygon\"," +
                "    \"coordinates\": " + Arrays.toString(coordinates) +
                "  }" +
                "}";
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the crop must not be null or blank.");
        }
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("The type of the crop must not be null or blank.");
        }
    }

}
