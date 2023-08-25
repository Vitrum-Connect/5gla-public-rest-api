package de.app.fivegla.integration.weenat.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Organization.
 * This class provides getter and setter methods for organization properties.
 */
@Getter
@Setter
public class Organization {
    private Long id;
    private String name;
    private String email;
}
