package de.app.fivegla.business;


import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.config.security.TenantCredentials;
import de.app.fivegla.event.events.CreateDefaultGroupForTenantEvent;
import de.app.fivegla.event.events.ResendSubscriptionsEvent;
import de.app.fivegla.persistence.GroupRepository;
import de.app.fivegla.persistence.ImageRepository;
import de.app.fivegla.persistence.TenantRepository;
import de.app.fivegla.persistence.ThirdPartyApiConfigurationRepository;
import de.app.fivegla.persistence.entity.Tenant;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService implements UserDetailsService {

    private final TenantRepository tenantRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GroupRepository groupRepository;
    private final ImageRepository imageRepository;
    private final ThirdPartyApiConfigurationRepository thirdPartyApiConfigurationRepository;

    /**
     * Creates a new tenant with the provided name and description.
     *
     * @param name        The name of the tenant. Must not be blank.
     * @param description The description of the tenant.
     * @return The created Tenant object.
     */
    @Transactional
    public TenantAndAccessToken create(@NotBlank String tenantId, @NotBlank String name, String description) {
        validateTenantId(tenantId);
        checkIfThereIsAlreadyATenantWithTheSameId(tenantId);
        log.info("Creating tenant with name: {} and description: {}", name, description);
        var tenant = new Tenant();
        tenant.setName(name);
        tenant.setDescription(description);
        tenant.setTenantId(tenantId);
        var accessToken = generateAccessToken();
        var encodedAccessToken = new BCryptPasswordEncoder().encode(accessToken);
        tenant.setAccessToken(encodedAccessToken);
        var tenantAndAccessToken = new TenantAndAccessToken(tenantRepository.save(tenant), accessToken);
        applicationEventPublisher.publishEvent(new ResendSubscriptionsEvent(this));
        applicationEventPublisher.publishEvent(new CreateDefaultGroupForTenantEvent(this, tenant));
        return tenantAndAccessToken;
    }

    private void validateTenantId(String tenantId) {
        if (!tenantId.matches("^[a-zA-Z0-9_]{1,50}$")) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.INVALID_TENANT_ID)
                    .message("The tenant ID must only contain alphanumeric characters.")
                    .build());
        }
    }

    private void checkIfThereIsAlreadyATenantWithTheSameId(String tenantId) {
        if (tenantRepository.findByTenantId(tenantId).isPresent()) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.TENANT_ALREADY_EXISTS)
                    .message("A tenant with the same ID already exists.")
                    .build());
        }
    }


    private String generateAccessToken() {
        log.info("Generating new access token for the the tenant.");
        var uuid = UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(uuid.getBytes());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalTenant = tenantRepository.findByTenantId(username);
        if (optionalTenant.isEmpty()) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.TENANT_NOT_FOUND)
                    .message("The tenant was not found.")
                    .build());
        } else {
            return new TenantCredentials(optionalTenant.get());
        }
    }

    /**
     * Finds all tenants in the system.
     *
     * @return A list of all tenants in the system.
     */
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    /**
     * Updates the tenant with the provided tenantId.
     *
     * @param tenantId    The tenantId of the tenant to update.
     * @param name        The new name of the tenant.
     * @param description The new description of the tenant.
     * @return The updated tenant.
     */
    @Transactional
    public Tenant update(String tenantId, String name, String description) {
        var tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.builder()
                        .error(Error.TENANT_NOT_FOUND)
                        .message("The tenant was not found.")
                        .build()));
        tenant.setName(name);
        tenant.setDescription(description);
        tenantRepository.save(tenant);
        return tenant;
    }

    /**
     * Fine a tenant by its tenantId.
     *
     * @param tenantId The tenantId of the tenant to find.
     * @return An Optional containing the tenant if it exists, or an empty Optional if the tenant doesn't exist.
     */
    public Optional<Tenant> findByTenantId(String tenantId) {
        return tenantRepository.findByTenantId(tenantId);
    }

    /**
     * Deletes a tenant by its tenantId.
     *
     * @param tenantId The tenantId of the tenant to delete.
     */
    @Transactional
    public void delete(String tenantId) {
        groupRepository.deleteByTenantTenantId(tenantId);
        imageRepository.deleteByTenantTenantId(tenantId);
        thirdPartyApiConfigurationRepository.deleteByTenantTenantId(tenantId);
        tenantRepository.deleteByTenantId(tenantId);
    }

    /**
     * Represents a combination of Tenant object and an access token.
     */
    public record TenantAndAccessToken(Tenant tenant, String accessToken) {
    }
}
