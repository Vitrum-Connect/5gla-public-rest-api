package de.app.fivegla.persistence.entity;

import de.app.fivegla.api.Manufacturer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class LastRun extends BaseEntity {

    /**
     * The id of the tenant.
     */
    private String tenantId;

    /**
     * The manufacturer of the third-party API.
     */
    @Enumerated(EnumType.STRING)
    private Manufacturer manufacturer;

    /**
     * The last run of the third-party API.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRun;

}
