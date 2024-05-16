package de.app.fivegla.integration.fiware.model.cygnus;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.integration.fiware.model.api.Validatable;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents an entity in the Fiware Integration Layer.
 * An entity can have an id and/or an id pattern, as well as a type and/or a type pattern.
 * <p>
 * This class provides getter and setter methods for each property, as well as a builder pattern for easy instantiation.
 * It also implements the Validatable interface, which allows the entity to be validated before use.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity implements Validatable {
    /**
     * The ID of the entity.
     */
    private String id;

    /**
     * The ID pattern of the entity.
     */
    private String idPattern;

    /**
     * The type of the entity.
     */
    private String type;

    /**
     * The type pattern of the entity.
     */
    private String typePattern;

    @Override
    public void validate() {
        if (StringUtils.isAllBlank(id, idPattern)) {
            throw new IllegalArgumentException("Either id or idPattern must be set");
        }
        if (StringUtils.isAllBlank(type, typePattern)) {
            throw new IllegalArgumentException("Either type or typePattern must be set");
        }
    }
}
