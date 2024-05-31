package de.app.fivegla.config.security.marker;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.persistence.entity.Tenant;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import java.security.Principal;

@SecurityScheme(type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "basic",
        name = "Authorization",
        description = "Use your custom credentials to authenticate. The credentials are created by creating a tenant in the system. See the documentation for more information."
)
@SecurityRequirement(name = "Authorization")
public interface TenantCredentialApiAccess {

    default Tenant validateTenant(TenantService tenantService, Principal principal) {
        var optionalTenant = tenantService.findByTenantId(principal.getName());
        if (optionalTenant.isEmpty()) {
            throw new BusinessException(ErrorMessage.builder().error(Error.TENANT_NOT_FOUND).message("The tenant was not found.").build());
        } else {
            return optionalTenant.get();
        }
    }

}
