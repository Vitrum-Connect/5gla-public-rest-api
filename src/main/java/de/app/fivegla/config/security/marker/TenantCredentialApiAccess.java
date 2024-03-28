package de.app.fivegla.config.security.marker;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        scheme = "basic",
        name = "Authorization",
        description = "Use your custom credentials to authenticate. The credentials are created by creating a tenant in the system. See the documentation for more information."
)
@SecurityRequirement(name = "Authorization")
public interface TenantCredentialApiAccess {
}
