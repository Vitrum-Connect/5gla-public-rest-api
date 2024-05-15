package de.app.fivegla.config.security;

import de.app.fivegla.controller.api.BaseMappings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * An interceptor class to handle API key authentication.
 * This class verifies the API key provided in the request header
 * against the configured API key. If the API key is not provided
 * or does not match the configured key, an UNAUTHORIZED response
 * is sent to the client.
 */
@Slf4j
public class ApiKeyInterceptor implements HandlerInterceptor {

    public static final String API_KEY_HEADER = "X-Api-Key";

    @Value("${app.api-key}")
    private String configuredApiKey;

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().contains(BaseMappings.SECURED_BY_API_KEY)) {
            log.debug("Skipping API key authentication for non-API requests, the request URI is: {}", request.getRequestURI());
        } else {
            var apiKeyHeader = request.getHeader(API_KEY_HEADER);
            if (apiKeyHeader == null || !apiKeyHeader.equals(configuredApiKey)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please provide a valid API key. See the documentation for more information.");
                return false;
            }
        }
        return true;
    }
}
