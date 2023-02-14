package de.app.fivegla.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


/**
 * The geolocation.
 */
@Getter
@Setter
@Schema(description = "The geolocation.")
public class GeoLocationDTO {

    /**
     * The latitude.
     */
    @Schema(description = "The latitude.")
    private double latitude;

    /**
     * The longitude.
     */
    @Schema(description = "The longitude.")
    private double longitude;

}
