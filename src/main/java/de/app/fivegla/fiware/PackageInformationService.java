package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.PropertyKeys;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Slf4j
@SuppressWarnings("unused")
public class PackageInformationService {

    private Properties properties;

    public PackageInformationService() {
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
