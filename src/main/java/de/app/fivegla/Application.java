package de.app.fivegla;

import de.app.fivegla.fiware.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The main class of the application.
 */
@EnableWebMvc
@EnableConfigurationProperties
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "5GLA Sensor Integration Services",
                version = "${app.version:unknown}",
                description = "This service provides the integration of multiple sensors with the 5GLA platform."
        )
)
public class Application {

    @Value("${app.fiware.contextBrokerUrl}")
    private String contextBrokerUrl;

    @Value("${app.fiware.notificationUrl}")
    private String notificationUrl;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

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
     * Dependency injection for the device integration service.
     *
     * @return -
     */
    @Bean
    public DeviceIntegrationService deviceIntegrationService() {
        return new DeviceIntegrationService(contextBrokerUrl);
    }

    /**
     * Dependency injection for the device measurement integration service.
     *
     * @return -
     */
    @Bean
    public DeviceMeasurementIntegrationService deviceMeasurementIntegrationService() {
        return new DeviceMeasurementIntegrationService(contextBrokerUrl);
    }

    /**
     * Dependency injection for the device measurement integration service.
     *
     * @return -
     */
    @Bean
    public DroneDeviceMeasurementIntegrationService droneDeviceMeasurementIntegrationService() {
        return new DroneDeviceMeasurementIntegrationService(contextBrokerUrl);
    }

    /**
     * Dependency injection for the status service.
     *
     * @return -
     */
    @Bean
    public StatusService statusService() {
        return new StatusService(contextBrokerUrl);
    }

    /**
     * Dependency injection for the subscription service.
     *
     * @return The SubscriptionService instance.
     */
    @Bean
    public SubscriptionService subscriptionService() {
        return new SubscriptionService(contextBrokerUrl, notificationUrl);
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
