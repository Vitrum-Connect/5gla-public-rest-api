package de.app.fivegla.controller.dto.request;

import de.app.fivegla.api.Manufacturer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request for device registration.
 * <p>
 * This class contains information about the manufacturer, ID, latitude, and longitude of a device
 * that needs to be registered.
 */
@Getter
@Setter
@Schema(description = "Represents a request for device registration.")
public class DeviceRegistrationRequest {

    /**
     * The manufacturer of the device.
     */
    @Schema(description = "The manufacturer of the device.")
    private Manufacturer manufacturer;

    /**
     * The ID of the device.
     * <p>
     * This variable stores the identifier of the device. It is represented as a String.
     * The ID is used to uniquely identify the device within a system.
     *
     * @since 1.0
     */
    @Schema(description = "The ID of the device.")
    private String id;

    /**
     * The latitude of the device.
     * <p>
     * This variable represents the latitude value of the device's location. It is a decimal number
     * specifying the north-south position on the Earth's surface. Positive values indicate locations
     * north of the equator, while negative values indicate locations south of the equator.
     * <p>
     * The latitude is measured in degrees and can range from -90 to 90.
     */
    @Schema(description = "The latitude of the device.")
    private double latitude;

    /**
     * The longitude of the device.
     * <p>
     * This variable stores the longitude value of the device. The longitude represents the east-west coordinate
     * of a point on the Earth's surface, measured in degrees. Negative values indicate locations west of
     * the Prime Meridian, and positive values indicate locations east of the Prime Meridian.
     * <p>
     * The longitude value should be a double type to allow for greater precision in representing decimal
     * degrees. The valid range for longitude is typically -180 to 180 degrees, where -180 represents the
     * International Date Line (IDL) in the Pacific Ocean and 180 represents the IDL in the Atlantic Ocean.
     * <p>
     * It is important to obtain accurate and up-to-date longitude values for location-based applications or
     * services. This can be done using GPS (Global Positioning System) or other positioning technologies
     * that provide reliable longitude data.
     */
    @Schema(description = "The longitude of the device.")
    private double longitude;

}
