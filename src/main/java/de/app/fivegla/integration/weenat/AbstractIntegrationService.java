package de.app.fivegla.integration.weenat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class AbstractIntegrationService {

    @Value("${app.sensors.weenat.url}")
    private String url;

    @Value("${app.sensors.weenat.username}")
    private String username;

    @Value("${app.sensors.weenat.password}")
    private String password;

}
