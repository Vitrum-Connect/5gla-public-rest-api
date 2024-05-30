package de.app.fivegla.persistence.entity;

import de.app.fivegla.api.Manufacturer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "third_party_api_configuration")
public class ThirdPartyApiConfiguration extends BaseEntity {

    /**
     * Represents the UUID of the third-party API. Will be set automatically to a random UUID.
     */
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    /**
     * Represents the tenant.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /**
     * Represents the manufacturer.
     */
    @Column(name = "manufacturer", nullable = false)
    @Enumerated(EnumType.STRING)
    private Manufacturer manufacturer;

    /**
     * Represents the prefix used for FIWARE integration.
     */
    @Column(name = "fiware_prefix")
    private String fiwarePrefix;

    /**
     * Indicator if the configuration is enabled.
     */
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    /**
     * Represents the URL of the third-party API.
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * Represents the username for the third-party API.
     */
    @Column(name = "username")
    private String username;

    /**
     * Represents the password for the third-party API.
     */
    @Column(name = "password")
    private String password;

    /**
     * Represents the API token for the third-party API.
     */
    @Column(name = "api_token")
    private String apiToken;

    /**
     * Represents the last run of the third-party API.
     */
    @Column(name = "last_run")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRun;
}
