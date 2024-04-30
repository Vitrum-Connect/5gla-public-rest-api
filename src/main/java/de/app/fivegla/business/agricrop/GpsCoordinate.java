package de.app.fivegla.business.agricrop;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GpsCoordinate {

    /**
     * Represents the latitude coordinate of a GPS location.
     *
     * The latitude value is stored as a double precision floating point number.
     */
    private double latitude;

    /**
     * Represents the longitude coordinate of a GPS location.
     *
     * The longitude value is stored as a double precision floating point number.
     */
    private double longitude;

}
