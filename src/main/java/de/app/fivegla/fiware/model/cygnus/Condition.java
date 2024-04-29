package de.app.fivegla.fiware.model.cygnus;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Condition implements Validatable {
    private List<String> attrs;
    private String expression;

    @Override
    public void validate() {
        // NOP
    }
}
