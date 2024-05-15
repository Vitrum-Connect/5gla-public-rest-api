package de.app.fivegla.config;

import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.StatusIntegrationService;
import de.app.fivegla.integration.fiware.SubscriptionIntegrationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

/**
 * The main class of the application.
 */
@Component
public class InternalBeanConfiguration {

    @Value("${app.fiware.contextBrokerUrl}")
    private String contextBrokerUrl;

    @Value("${app.fiware.subscriptions.notificationUrls}")
    private String[] notificationUrls;

    /**
     * Dependency injection for the model mapper.
     *
     * @return -
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Dependency injection for the status service.
     *
     * @return -
     */
    @Bean
    public StatusIntegrationService statusService() {
        return new StatusIntegrationService(contextBrokerUrl, "unused");
    }

    /**
     * Dependency injection for the subscription service.
     *
     * @return The SubscriptionService instance.
     */
    public SubscriptionIntegrationService subscriptionService(String tenant) {
        return new SubscriptionIntegrationService(contextBrokerUrl, tenant, List.of(notificationUrls));
    }

    /**
     * Dependency injection for the device measurement integration service.
     *
     * @return -
     */
    public FiwareEntityIntegrationService fiwareEntityIntegrationService(String tenant) {
        return new FiwareEntityIntegrationService(contextBrokerUrl, tenant);
    }

    /**
     * Dependency injection for the rest template.
     *
     * @return -
     */
    @Bean
    @Scope("prototype")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
