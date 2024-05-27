package de.app.fivegla.persistence.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a tenant in the system.
 */
@Entity
@Getter
@Setter
public class Tenant extends BaseEntity {

    /**
     * The creation date of the tenant.
     */
    private Instant createdAt;

    /**
     * The name of the tenant.
     */
    private String name;

    /**
     * The description of the tenant.
     */
    private String description;

    /**
     * The UUID of the tenant.
     */
    private String tenantId;

    /**
     * The access token of the tenant.
     */
    private String accessToken;

    public String getFiwarePrefix() {
        return "urn:ngsi-ld:" + tenantId.replace("_", "-") + ":";
    }
}
