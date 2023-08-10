package de.app.fivegla;

import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.DroneDeviceMeasurementIntegrationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The main class of the application.
 */
@EnableWebMvc
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "5GLA Sensor Integration Services",
                version = "${app.version:unknown}",
                description = "This service provides the integration of multiple sensors with the 5GLA platform."
        )
)
public class Application {

    @Value("${fiware.context-broker-url}")
    private String contextBrokerUrl;

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
}
