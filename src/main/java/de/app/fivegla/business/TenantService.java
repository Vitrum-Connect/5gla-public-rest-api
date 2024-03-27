package de.app.fivegla.business;


import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.persistence.ApplicationDataRepository;
import de.app.fivegla.persistence.entity.Tenant;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

    private final ApplicationDataRepository applicationDataRepository;

    /**
     * Creates a new tenant with the provided name and description.
     *
     * @param name        The name of the tenant. Must not be blank.
     * @param description The description of the tenant.
     * @return The created Tenant object.
     */
    public Tenant create(@NotBlank String tenantId, @NotBlank String name, String description) {
        validateTenantId(tenantId);
        checkIfThereIsAlreadyATenantWithTheSameId(tenantId);
        log.info("Creating tenant with name: {} and description: {}", name, description);
        var tenant = new Tenant();
        tenant.setCreatedAt(Instant.now());
        tenant.setName(name);
        tenant.setDescription(description);
        tenant.setUuid(tenantId);
        var accessToken = generateAccessToken();
        var encodedAccessToken = new BCryptPasswordEncoder().encode(accessToken);
        tenant.setAccessToken(encodedAccessToken);
        return applicationDataRepository.addTenant(tenant);
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
}
