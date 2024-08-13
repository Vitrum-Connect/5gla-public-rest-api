package de.app.fivegla.business;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.event.events.HistoricalDataImportEvent;
import de.app.fivegla.persistence.ThirdPartyApiConfigurationRepository;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThirdPartyApiConfigurationService {

    private final ThirdPartyApiConfigurationRepository thirdPartyApiConfigurationRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a third-party API configuration and adds it to the system.
     *
     * @param configuration The third-party API configuration to be created and added.
     * @return The created third-party API configuration.
     */
    @Transactional
    public ThirdPartyApiConfiguration createThirdPartyApiConfiguration(ThirdPartyApiConfiguration configuration) {
        log.info("Creating third-party API configuration.");
        return thirdPartyApiConfigurationRepository.save(configuration);
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
        return thirdPartyApiConfigurationRepository.findAllByTenantTenantIdAndUuid(tenantId, uuid);
    }

    /**
     * Deletes a third-party API configuration.
     *
     * @param tenantId The tenantId of the third-party API configuration.
     * @param uuid     The uuid of the third-party API configuration.
     */
    @Transactional
    public void deleteThirdPartyApiConfiguration(String tenantId, String uuid) {
        log.info("Deleting third-party API configuration.");
        thirdPartyApiConfigurationRepository.deleteByTenantTenantIdAndUuid(tenantId, uuid);
    }

    /**
     * Gets all third-party API configurations.
     *
     * @param tenantId The tenantId of the third-party API configuration.
     * @return A list of third-party API configurations.
     */
    public List<ThirdPartyApiConfiguration> getThirdPartyApiConfigurations(String tenantId) {
        return thirdPartyApiConfigurationRepository.findAllByTenantTenantId(tenantId);
    }

    /**
     * Updates the last run of a third-party API configuration.
     *
     * @param thirdPartyApiConfiguration The third-party API configuration to be updated.
     */
    @Transactional
    public void updateLastRun(ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        thirdPartyApiConfiguration.setLastRun(Date.from(Instant.now()));
        thirdPartyApiConfigurationRepository.save(thirdPartyApiConfiguration);
    }

    /**
     * Finds a ThirdPartyApiConfiguration by its ID.
     *
     * @param id The ID of the ThirdPartyApiConfiguration.
     * @return An Optional containing the ThirdPartyApiConfiguration with the given ID, or an empty Optional if not found.
     */
    public Optional<ThirdPartyApiConfiguration> findById(Long id) {
        return thirdPartyApiConfigurationRepository.findById(id);
    }

    /**
     * Triggers the import of historical data for a given tenant and UUID starting from a specific date.
     *
     * @param tenantId           the ID of the tenant for which the import will be triggered
     * @param uuid               the UUID associated with the tenant
     * @param startDateInThePast the start date from which the import will begin, in the past
     */
    public void triggerImport(String tenantId, String uuid, LocalDate startDateInThePast) {
        getThirdPartyApiConfigurations(tenantId, uuid).forEach(thirdPartyApiConfiguration -> applicationEventPublisher.publishEvent(new HistoricalDataImportEvent(this, thirdPartyApiConfiguration.getId(),
                startDateInThePast.atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }

    /**
     * Finds all third-party API configurations for a given tenant and manufacturer.
     *
     * @param tenant       the tenant for which the configurations will be retrieved
     * @param manufacturer the manufacturer for which the configurations will be retrieved
     */
    public Optional<ThirdPartyApiConfiguration> findByManufacturer(Tenant tenant, Manufacturer manufacturer) {
        return thirdPartyApiConfigurationRepository.findFirstByTenantAndManufacturer(tenant, manufacturer);
    }
}
