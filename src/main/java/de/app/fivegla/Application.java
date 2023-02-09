package de.app.fivegla;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
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
                title = "5GLA REST API",
                version = "${app.version:unknown}",
                description = "REST API for the 5GLA environment. This API ensures, that the sensors can be registered and the data can be sent to the 5GLA environment."
        )
)
public class Application {

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

}
