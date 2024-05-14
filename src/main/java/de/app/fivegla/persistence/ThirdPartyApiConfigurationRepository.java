package de.app.fivegla.persistence;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ThirdPartyApiConfigurationRepository {

    private final ApplicationData applicationData;


    /**
     * Add third party API configuration.
     */
    public void addThirdPartyApiConfiguration(ThirdPartyApiConfiguration configuration) {
        if (applicationData.getThirdPartyApiConfigurations() == null) {
            applicationData.setThirdPartyApiConfigurations(List.of(configuration));
        } else {
            applicationData.getThirdPartyApiConfigurations().add(configuration);
        }
        applicationData.persist();
    }

    /**
     * Retrieves a list of ThirdPartyApiConfigurations based on the provided tenant ID.
     *
     * @param name The ID of the tenant.
     * @return A list of ThirdPartyApiConfigurations that match the given tenant ID.
     */
    public List<ThirdPartyApiConfiguration> getThirdPartyApiConfigurations(String name) {
        if (applicationData.getThirdPartyApiConfigurations() == null) {
            return Collections.emptyList();
        } else {
            return applicationData.getThirdPartyApiConfigurations().stream().filter(configuration -> configuration.getTenantId().equals(name)).toList();
        }
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param tenantId     The ID of the tenant for which to delete the third-party API configuration.
     * @param manufacturer The manufacturer of the third-party API configuration.
     */
    public void deleteThirdPartyApiConfiguration(String tenantId, Manufacturer manufacturer) {
        applicationData.getThirdPartyApiConfigurations().removeIf(configuration -> configuration.getTenantId().equals(tenantId) && configuration.getManufacturer().equals(manufacturer));
        applicationData.persist();
    }


}
