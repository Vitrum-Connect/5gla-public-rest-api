package de.app.fivegla.controller.security;

import de.app.fivegla.config.security.ApiKeyInterceptor;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        name = ApiKeyInterceptor.API_KEY_HEADER,
        paramName = ApiKeyInterceptor.API_KEY_HEADER,
        description = "Please provide a valid API key. See the documentation for more information."
)
@SecurityRequirement(name = ApiKeyInterceptor.API_KEY_HEADER)
public interface SecuredApiAccess {
}
