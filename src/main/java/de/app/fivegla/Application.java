package de.app.fivegla;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The main class of the application.
 */
@EnableWebMvc
@SpringBootApplication
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "5GLA REST API",
                version = "1.0",
                description = "REST API for the 5GLA environment. This API ensures, that the sensors can be registered and the data can be sent to the 5GLA environment."
        ),
        servers = @io.swagger.v3.oas.annotations.servers.Server(
                url = "n.a.",
                description = "Currently not available."
        )
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
