package de.app.fivegla.business;

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

    public List<ThirdPartyApiConfiguration> getThirdPartyApiConfigurations(String name) {
        log.info("Getting third-party API configurations.");
        return applicationDataRepository.getThirdPartyApiConfigurations(name);
    }
}
