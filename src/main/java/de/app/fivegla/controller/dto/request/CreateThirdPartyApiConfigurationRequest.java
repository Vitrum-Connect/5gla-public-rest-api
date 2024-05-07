package de.app.fivegla.controller.dto.request;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Represents a request to create a third-party API configuration.")
public class CreateThirdPartyApiConfigurationRequest extends BaseRequest {

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
     * Converts a CreateThirdPartyApiConfigurationRequest object to a ThirdPartyApiConfiguration object.
     *
     * @return The converted ThirdPartyApiConfiguration object.
     */
    public ThirdPartyApiConfiguration toEntity() {
        var configuration = new ThirdPartyApiConfiguration();
        configuration.setManufacturer(manufacturer);
        configuration.setFiwarePrefix(fiwarePrefix);
        configuration.setEnabled(enabled);
        configuration.setUrl(url);
        configuration.setUsername(username);
        configuration.setPassword(password);
        configuration.setApiToken(apiToken);
        configuration.setZone(getZone());
        return configuration;
    }
}
