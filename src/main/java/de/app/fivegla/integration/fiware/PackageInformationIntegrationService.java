package de.app.fivegla.integration.fiware;

import de.app.fivegla.integration.fiware.api.PropertyKeys;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Slf4j
@SuppressWarnings("unused")
public class PackageInformationIntegrationService {

    private Properties properties;

    public PackageInformationIntegrationService() {
        initProperties();
    }

    private void initProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/package.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load package.properties", e);
        }
    }

    /**
     * Returns the current version of the package.
     *
     * @return the current version
     */
    @SuppressWarnings("unused")
    public String getCurrentVersion() {
        return properties.getProperty(PropertyKeys.APP_VERSION);
    }

}
