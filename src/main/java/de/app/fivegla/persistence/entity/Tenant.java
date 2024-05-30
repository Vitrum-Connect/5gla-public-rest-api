package de.app.fivegla.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a tenant in the system.
 */
@Entity
@Getter
@Setter
@Table(name = "tenant", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenant_id"})
})
public class Tenant extends BaseEntity {

    /**
     * The UUID of the tenant.
     */
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    /**
     * The name of the tenant.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description of the tenant.
     */
    @Column(name = "description")
    private String description;

    /**
     * The access token of the tenant.
     */
    @Column(name = "access_token", nullable = false)
    private String accessToken;

    public String getFiwarePrefix() {
        return "urn:ngsi-ld:" + tenantId.replace("_", "-") + ":";
    }
}
