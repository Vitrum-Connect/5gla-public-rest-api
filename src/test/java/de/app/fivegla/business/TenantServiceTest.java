package de.app.fivegla.business;

import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.persistence.ApplicationDataRepository;
import de.app.fivegla.persistence.entity.Tenant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TenantServiceTest {

    @Mock
    private ApplicationDataRepository applicationDataRepository;

    private TenantService tenantService;

    private AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        tenantService = new TenantService(applicationDataRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    // Test case: Creating a tenant with valid inputs
    @Test
    public void givenValidInputs_whenCreatingTenant_ThenTheTenantShouldBeCreated() {
        String tenantId = "validTenantId";
        String name = "validName";
        String description = "validDescription";
        String accessToken = "accessToken";
        // For tenantId validation
        when(applicationDataRepository.getTenant(tenantId)).thenReturn(Optional.empty());
        Tenant expectedTenant = new Tenant();
        expectedTenant.setCreatedAt(Instant.now());
        expectedTenant.setName(name);
        expectedTenant.setDescription(description);
        expectedTenant.setUuid(tenantId);
        expectedTenant.setAccessToken(accessToken);
        when(applicationDataRepository.addTenant(any(Tenant.class))).thenReturn(expectedTenant);
        Tenant actualTenant = tenantService.create(tenantId, name, description);
        assertEquals(expectedTenant, actualTenant);
    }

    // Test case: Tenant already exists with same id.
    @Test
    public void givenDuplicateTenant_whenCreatingTenant_ThenTheTenantShouldNotBeCreated() {
        String tenantId = "validTenantId";
        String name = "validName";
        String description = "validDescription";
        Tenant existingTenant = new Tenant();
        existingTenant.setUuid(tenantId);
        when(applicationDataRepository.getTenant(tenantId)).thenReturn(Optional.of(existingTenant));
        assertThrows(BusinessException.class, () -> tenantService.create(tenantId, name, description));
    }

    // Test case: Tenant Id is invalid
    @Test
    public void givenInvalidTenantId_whenCreatingTenant_ThenTheTenantShouldNotBeCreated() {
        String tenantId = "invalid Tenant Id!!";
        String name = "validName";
        String description = "validDescription";
        assertThrows(BusinessException.class, () -> tenantService.create(tenantId, name, description));
    }
}