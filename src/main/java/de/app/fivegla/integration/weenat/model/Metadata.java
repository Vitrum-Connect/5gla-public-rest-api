package de.app.fivegla.integration.weenat.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the metadata for a specific entity.
 * It contains information such as id, name, latitude, longitude, device count and organization.
 */
@Getter
@Setter
public class Metadata {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Integer deviceCount;
    private Organization organization;
}
