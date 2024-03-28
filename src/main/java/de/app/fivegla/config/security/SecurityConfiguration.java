package de.app.fivegla.config.security;

import de.app.fivegla.business.TenantService;
import de.app.fivegla.controller.api.BaseMappings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static de.app.fivegla.controller.api.BaseMappings.API_V1;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String WILDCARD = "/**";

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final TenantService tenantService;

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(tenantService);
    }

    @Bean
    public WebMvcConfigurer cors() {
        return new WebMvcConfigurer() {
            @Override
            @SuppressWarnings("NullableProblems")
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(API_V1 + "/**")
                        .allowedOrigins(allowedOrigins);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(requests ->
                        requests.requestMatchers(BaseMappings.SWAGGER_UI + WILDCARD,
                                        BaseMappings.SWAGGER_V3 + WILDCARD,
                                        BaseMappings.ACTUATOR + WILDCARD,
                                        BaseMappings.SECURED_BY_API_KEY + WILDCARD,
                                        BaseMappings.ERROR + WILDCARD).permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint))
                .csrf().disable()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
