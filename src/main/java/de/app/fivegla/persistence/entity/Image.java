package de.app.fivegla.persistence.entity;

import de.app.fivegla.persistence.entity.enums.ImageChannel;
import jakarta.persistence.*;
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
@Table(name = "image")
public class Image {

    /**
     * The oid of the image.
     */
    @Column(name = "oid", nullable = false, unique = true)
    private String oid;

    /**
     * The id of the drone.
     */
    @Column(name = "drone_id", nullable = false)
    private String droneId;

    /**
     * The transaction id.
     */
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    /**
     * The channel of the image since the value can not be read from the EXIF.
     */
    @Column(name = "channel", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageChannel channel;

    /**
     * The base64 encoded tiff image.
     */
    @Lob
    @Column(name = "base64_image", nullable = false)
    private String base64Image;

    /**
     * The time the image was taken.
     */
    @Column(name = "measured_at", nullable = false)
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
