package de.app.fivegla.persistence.entity;

import de.app.fivegla.api.Manufacturer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThirdPartyApiConfiguration extends BaseEntity {

    /**
     * Represents the tenant.
     */
    private String tenantId;

    /**
     * Represents the manufacturer.
     */
    private Manufacturer manufacturer;

    /**
     * Represents the prefix used for FIWARE integration.
     */
    private String fiwarePrefix;

    /**
     * Indicator if the configuration is enabled.
     */
    private boolean enabled;

    /**
     * Represents the URL of the third-party API.
     */
    private String url;

    /**
     * Represents the username for the third-party API.
     */
    private String username;

    /**
     * Represents the password for the third-party API.
     */
    private String password;

    /**
     * Represents the API token for the third-party API.
     */
    private String apiToken;

    /**
     * Represents the UUID of the third-party API. Will be set automatically to a random UUID.
     */
    private String uuid;

}
