package de.app.fivegla.persistence.entity;

import de.app.fivegla.api.Manufacturer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "last_run", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenant_id", "manufacturer"})
})
public class LastRun extends BaseEntity {

    /**
     * The id of the tenant.
     */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * The manufacturer of the third-party API.
     */
    @Column(name = "manufacturer", nullable = false)
    @Enumerated(EnumType.STRING)
    private Manufacturer manufacturer;

    /**
     * The last run of the third-party API.
     */
    @Column(name = "last_run", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRun;

}
