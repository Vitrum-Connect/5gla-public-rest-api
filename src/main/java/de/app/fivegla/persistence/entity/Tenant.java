package de.app.fivegla.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a tenant in the system.
 */
@Getter
@Setter
public class Tenant {

    private Instant createdAt;

    private String name;

    private String description;

    private String uuid;

    private String accessToken;

}
