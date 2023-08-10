package de.app.fivegla.integration.agvolution;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

@SpringBootTest
class AgvolutionSensorDataIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AgvolutionSensorDataIntegrationService agvolutionSensorDataIntegrationService;
    @Autowired
    private AgvolutionSensorIntegrationService agvolutionSensorIntegrationService;

    @Test
    void givenValidCredentialsWhenGetDevicesThenTheRequestShouldBeAccepted() {
        agvolutionSensorIntegrationService.findAll()
                .stream()
                .filter(device -> device.getId().equals("68500F317F38DBC7"))
                .forEach(device -> {
                    var timeSeries = agvolutionSensorDataIntegrationService.findAll(device, Instant.now().minus(1, DAYS));
                    Assertions.assertNotNull(timeSeries);
                    Assertions.assertFalse(timeSeries.isEmpty());
                });
    }

}