package de.app.fivegla.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * An interceptor class to handle API key authentication.
 * This class verifies the API key provided in the request header
 * against the configured API key. If the API key is not provided
 * or does not match the configured key, an UNAUTHORIZED response
 * is sent to the client.
 */
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${app.api-key}")
    private String configuredApiKey;

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var apiKeyHeader = request.getHeader("X-Api-Key");
        if (apiKeyHeader == null || !apiKeyHeader.equals(configuredApiKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please provide a valid API key. See the documentation for more information.");
            return false;
        }
        return true;
    }
}
