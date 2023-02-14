package de.app.fivegla.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Geo location.
 */
@Getter
@Setter
@Builder
public class GeoLocation {

    /**
     * The latitude.
     */
    private double latitude;

    /**
     * The longitude.
     */
    private double longitude;

}
