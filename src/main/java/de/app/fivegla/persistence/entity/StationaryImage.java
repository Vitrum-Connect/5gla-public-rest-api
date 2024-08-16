package de.app.fivegla.persistence.entity;

import de.app.fivegla.persistence.entity.enums.ImageChannel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.Date;

/**
 * Image.
 */
@Entity
@Getter
@Setter
@Table(name = "image")
public class StationaryImage extends BaseEntity {

    /**
     * The oid of the image.
     */
    @Column(name = "oid", nullable = false, unique = true)
    private String oid;

    /**
     * The id of the camera.
     */
    @Column(name = "camera_id", nullable = false)
    private String cameraId;

    /**
     * The channel of the image since the value cannot be read from the EXIF.
     */
    @Column(name = "image_channel", nullable = false)
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
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * The tenant of the image.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /**
     * Returns the image as raw data.
     *
     * @return the image as raw data
     */
    public byte[] getImageAsRawData() {
        return Base64.getDecoder().decode(base64encodedImage);
    }

    /**
     * Returns the name of the image.
     *
     * @return the name of the image
     */
    public String getFullFilename(Tenant tenant) {
        return tenant.getTenantId() + "/" + cameraId + "/" + channel.name() + "/" + measuredAt.toInstant().getEpochSecond() + ".tiff";
    }
}
