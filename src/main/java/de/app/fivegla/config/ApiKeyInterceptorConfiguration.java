package de.app.fivegla.config;

import de.app.fivegla.config.security.ApiKeyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for the API key interceptor.
 * Implements the WebMvcConfigurer interface to configure the web application's InterceptorRegistry.
 */
@Configuration
public class ApiKeyInterceptorConfiguration implements WebMvcConfigurer {

    /**
     * Adds the API key interceptor to the specified InterceptorRegistry.
     * This method is invoked when configuring the web application's InterceptorRegistry.
     *
     * @param registry the InterceptorRegistry to add the interceptor to
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor());
    }

    /**
     * Returns a new instance of ApiKeyInterceptor as a HandlerInterceptor.
     * This method is used as a Bean in a Spring application context configuration.
     *
     * @return A new instance of ApiKeyInterceptor as a HandlerInterceptor
     */
    @Bean
    public HandlerInterceptor apiKeyInterceptor() {
        return new ApiKeyInterceptor();
    }
}
