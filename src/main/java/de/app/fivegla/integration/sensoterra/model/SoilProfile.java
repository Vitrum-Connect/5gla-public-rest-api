package de.app.fivegla.integration.sensoterra.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Response object.
 */
@Getter
@Setter
public class SoilProfile {
    private int depth;
    private String soil;
}
