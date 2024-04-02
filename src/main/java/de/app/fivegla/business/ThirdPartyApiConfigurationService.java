package de.app.fivegla.business;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.persistence.ApplicationDataRepository;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyApiConfigurationService {

    private final ApplicationDataRepository applicationDataRepository;

    /**
     * Creates a third-party API configuration and adds it to the system.
     *
     * @param configuration The third-party API configuration to be created and added.
     */
    public void createThirdPartyApiConfiguration(ThirdPartyApiConfiguration configuration) {
        log.info("Creating third-party API configuration.");
        applicationDataRepository.addThirdPartyApiConfiguration(configuration);
    }

    /**
     * Gets all third-party API configurations.
     *
     * @param tenantId The tenantId of the third-party API configuration.
     * @return A list of third-party API configurations.
     */
    public List<ThirdPartyApiConfiguration> getThirdPartyApiConfigurations(String tenantId) {
        log.info("Getting third-party API configurations.");
        return applicationDataRepository.getThirdPartyApiConfigurations(tenantId);
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param tenantId         The tenantId of the third-party API configuration.
     * @param manufacturer The manufacturer of the third-party API configuration.
     */
    public void deleteThirdPartyApiConfiguration(String tenantId, Manufacturer manufacturer) {
        log.info("Deleting third-party API configuration.");
        applicationDataRepository.deleteThirdPartyApiConfiguration(tenantId, manufacturer);
    }
}
