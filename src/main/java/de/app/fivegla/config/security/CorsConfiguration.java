package de.app.fivegla.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static de.app.fivegla.controller.api.BaseMappings.API_V1;

/**
 * A configuration class for Cross-Origin Resource Sharing (CORS).
 */
@Configuration
public class CorsConfiguration {

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            @SuppressWarnings("NullableProblems")
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(API_V1 + "/**")
                        .allowedOrigins(allowedOrigins);
            }
        };
    }

}
