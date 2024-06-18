package de.app.fivegla.config;

import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.StatusIntegrationService;
import de.app.fivegla.integration.fiware.SubscriptionIntegrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
        return new StatusIntegrationService(contextBrokerUrl);
    }

    /**
     * Dependency injection for the device measurement integration service.
     *
     * @return -
     */
    @Bean
    public FiwareEntityIntegrationService fiwareEntityIntegrationService() {
        return new FiwareEntityIntegrationService(contextBrokerUrl);
    }

    /**
     * Dependency injection for the subscription service.
     *
     * @return The SubscriptionService instance.
     */
    @Bean
    public SubscriptionIntegrationService subscriptionIntegrationService() {
        return new SubscriptionIntegrationService(contextBrokerUrl, List.of(notificationUrls));
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
