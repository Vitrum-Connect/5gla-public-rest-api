package de.app.fivegla.fiware.model.cygnus;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import lombok.*;

import java.util.List;

/**
 * Represents a subject in a system.
 * A subject can have a list of entities and conditions.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subject implements Validatable {
    /**
     * The entities.
     */
    private List<Entity> entities;

    /**
     * The condition.
     */
    private Condition condition;

    @Override
    public void validate() {
        if (entities == null || entities.isEmpty()) {
            throw new RuntimeException("Entities must not be null or empty");
        }
        for (Entity entity : entities) {
            entity.validate();
        }
        if (condition != null) {
            condition.validate();
        }
    }
}
