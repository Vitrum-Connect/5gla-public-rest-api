package de.app.fivegla.persistence.entity;

import de.app.fivegla.persistence.entity.enums.ImageChannel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Image.
 */
@Entity
@Getter
@Setter
@Table(name = "image")
public class Image extends BaseEntity {

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
    @Column(name = "base64_encoded_image", nullable = false)
    private String base64encodedImage;

    /**
     * The time the image was taken.
     */
    @Column(name = "measured_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date measuredAt;

    /**
     * The location of the image.
     */
    @Column(name = "longitude", nullable = false)
    private double longitude;

    /**
     * The location of the image.
     */
    @Column(name = "latitude", nullable = false)
    private double latitude;

    /**
     * The group of the image.
     */
    // TODO: Change to a default group in case the group is deleted.
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * The tenant of the image.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}
