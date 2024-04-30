package de.app.fivegla.business;


import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.event.events.ResendSubscriptionsEvent;
import de.app.fivegla.persistence.ApplicationDataRepository;
import de.app.fivegla.persistence.entity.Tenant;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService implements UserDetailsService {

    private final ApplicationDataRepository applicationDataRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new tenant with the provided name and description.
     *
     * @param name        The name of the tenant. Must not be blank.
     * @param description The description of the tenant.
     * @return The created Tenant object.
     */
    public TenantAndAccessToken create(@NotBlank String tenantId, @NotBlank String name, String description) {
        validateTenantId(tenantId);
        checkIfThereIsAlreadyATenantWithTheSameId(tenantId);
        log.info("Creating tenant with name: {} and description: {}", name, description);
        var tenant = new Tenant();
        tenant.setCreatedAt(Instant.now());
        tenant.setName(name);
        tenant.setDescription(description);
        tenant.setTenantId(tenantId);
        var accessToken = generateAccessToken();
        var encodedAccessToken = new BCryptPasswordEncoder().encode(accessToken);
        tenant.setAccessToken(encodedAccessToken);
        var tenantAndAccessToken = new TenantAndAccessToken(applicationDataRepository.addTenant(tenant), accessToken);
        applicationEventPublisher.publishEvent(new ResendSubscriptionsEvent(this));
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
        if (applicationDataRepository.getTenant(tenantId).isPresent()) {
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
        return applicationDataRepository.getTenant(username)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found: " + username));
    }

    /**
     * Finds all tenants in the system.
     *
     * @return A list of all tenants in the system.
     */
    public List<Tenant> findAll() {
        return applicationDataRepository.findTenants();
    }

    /**
     * Updates the tenant with the provided tenantId.
     *
     * @param tenantId    The tenantId of the tenant to update.
     * @param name        The new name of the tenant.
     * @param description The new description of the tenant.
     * @return The updated tenant.
     */
    public Tenant update(String tenantId, String name, String description) {
        checkIfThereIsAlreadyATenantWithTheSameId(tenantId);
        var tenant = applicationDataRepository.updateTenant(tenantId, name, description);
        applicationEventPublisher.publishEvent(new ResendSubscriptionsEvent(this));
        return tenant;
    }

    /**
     * Represents a combination of Tenant object and an access token.
     */
    public record TenantAndAccessToken(Tenant tenant, String accessToken) {
    }
}
