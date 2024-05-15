package de.app.fivegla;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The main class of the application.
 */
@EnableAsync
@EnableWebMvc
@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "5gLa API",
                version = "${app.version:unknown}",
                description = "This service provides the integration of multiple sensors with the 5gLa platform. " +
                        "It is part of the 5GLA project, which is funded by the German Federal Ministry of Transport and Digital Infrastructure (BMVI). " +
                        "The website of the project is https://www.5gla.de/, you can find all additional information there. If you are interested in the source code, " +
                        "you can find it on GitHub: https://github.com/vitrum-connect/5gla-sensor-integration-services",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(
                        name = "5GLA Team",
                        url = "https://www.5gla.de/"),
                termsOfService = "https://www.5gla.de/"
        ),
        servers = {
                @Server(
                        description = "PROD | n.a. at the moment",
                        url = "https://api.5gla.de/api"
                ),
                @Server(
                        description = "QA",
                        url = "https://api.qa.5gla.de/api"
                ),
                @Server(
                        description = "DEV",
                        url = "https://api.dev.5gla.de/api"
                ),
                @Server(
                        description = "Local Development",
                        url = "http://localhost:8080"
                )
        }
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
