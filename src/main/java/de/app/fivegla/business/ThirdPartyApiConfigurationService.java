package de.app.fivegla.business;

import de.app.fivegla.persistence.ThirdPartyApiConfigurationRepository;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyApiConfigurationService {

    private final ThirdPartyApiConfigurationRepository thirdPartyApiConfigurationRepository;

    /**
     * Creates a third-party API configuration and adds it to the system.
     *
     * @param configuration The third-party API configuration to be created and added.
     * @return The created third-party API configuration.
     */
    public ThirdPartyApiConfiguration createThirdPartyApiConfiguration(ThirdPartyApiConfiguration configuration) {
        log.info("Creating third-party API configuration.");
        return thirdPartyApiConfigurationRepository.addThirdPartyApiConfiguration(configuration);
    }

    /**
     * Gets all third-party API configurations.
     *
     * @param tenantId The tenantId of the third-party API configuration.
     * @param uuid     The uuid of the third-party API configuration.
     * @return A list of third-party API configurations.
     */
    public List<ThirdPartyApiConfiguration> getThirdPartyApiConfigurations(String tenantId, String uuid) {
        log.info("Getting third-party API configurations.");
        return thirdPartyApiConfigurationRepository.getThirdPartyApiConfigurations(tenantId, uuid);
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param tenantId The tenantId of the third-party API configuration.
     * @param uuid     The uuid of the third-party API configuration.
     */
    public void deleteThirdPartyApiConfiguration(String tenantId, String uuid) {
        log.info("Deleting third-party API configuration.");
        thirdPartyApiConfigurationRepository.deleteThirdPartyApiConfiguration(tenantId, uuid);
    }

    /**
     * Gets all third-party API configurations.
     *
     * @param tenantId The tenantId of the third-party API configuration.
     * @return A list of third-party API configurations.
     */
    public List<ThirdPartyApiConfiguration> getThirdPartyApiConfigurations(String tenantId) {
        return thirdPartyApiConfigurationRepository.getThirdPartyApiConfigurations(tenantId);
    }
}
