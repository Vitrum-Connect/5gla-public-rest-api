package de.app.fivegla.persistence.entity;

import de.app.fivegla.persistence.entity.enums.ImageChannel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.Date;

/**
 * Image.
 */
@Getter
@Setter
@Builder
public class Image {

    /**
     * The oid of the image.
     */
    private String oid;

    /**
     * The id of the drone.
     */
    private String droneId;

    /**
     * The transaction id.
     */
    private String transactionId;

    /**
     * The channel of the image since the value can not be read from the EXIF.
     */
    @Enumerated(EnumType.STRING)
    private ImageChannel channel;

    /**
     * The base64 encoded tiff image.
     */
    private String base64Image;

    /**
     * The time the image was taken.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date measuredAt;

    /**
     * The location of the image.
     */
    // FIXME The location should be a Point, but the Point class is not serializable.
    private Point location;

    /**
     * The group of the image.
     */
    private String group;
}
