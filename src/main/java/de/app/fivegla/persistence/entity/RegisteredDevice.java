package de.app.fivegla.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Image.
 */
@Entity
@Getter
@Setter
@Table(name = "registered_device")
public class RegisteredDevice extends BaseEntity {

    /**
     * The oid.
     */
    @Column(name = "oid", nullable = false, unique = true)
    private String oid;

    /**
     * The name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * The location of the image.
     */
    @Column(name = "longitude", nullable = false)
    private double longitude;

    /**
     * The location.
     */
    @Column(name = "latitude", nullable = false)
    private double latitude;

    /**
     * The group.
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * The tenant.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

}
