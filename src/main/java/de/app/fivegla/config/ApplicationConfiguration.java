package de.app.fivegla.config;

import de.app.fivegla.api.Manufacturer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationConfiguration {
    private String version;
    private ManufacturerConfiguration sensors;
    private String scheduledDataImportCron;

    /**
     * Determines if the specified manufacturer's sensor is enabled.
     *
     * @param manufacturer the manufacturer to check
     * @return true if the sensor is enabled, false otherwise
     */
    public boolean isEnabled(Manufacturer manufacturer) {
        return switch (manufacturer) {
            case SENSOTERRA -> sensors.sensoterra().enabled();
            case FARM21 -> sensors.farm21().enabled();
            case SOILSCOUT -> sensors.soilscout().enabled();
            case AGRANIMO -> sensors.agranimo().enabled();
            case MICA_SENSE -> sensors.micasense().enabled();
            case AGVOLUTION -> sensors.agvolution().enabled();
        };
    }
}
