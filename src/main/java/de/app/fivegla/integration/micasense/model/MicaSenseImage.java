package de.app.fivegla.integration.micasense.model;

import de.app.fivegla.fiware.model.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * MicaSense image.
 */
@Getter
@Setter
@Builder
public class MicaSenseImage {

    /**
     * The oid of the image.
     */
    private String oid;

    /**
     * The id of the drone.
     */
    private String droneId;

    /**
     * The channel of the image since the value can not be read from the EXIF.
     */
    private MicaSenseChannel channel;

    /**
     * The base64 encoded tiff image.
     */
    private String base64Image;

    /**
     * The time the image was taken.
     */
    private Instant measuredAt;

    /**
     * The location of the image.
     */
    private Location location;
}
