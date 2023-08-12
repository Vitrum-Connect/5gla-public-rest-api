package de.app.fivegla.integration.agvolution;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgvolutionSensorIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AgvolutionSensorIntegrationService agvolutionSensorIntegrationService;

    @Test
    void givenValidCredentialsWhenGetDevicesThenTheRequestShouldBeAccepted() {
        var allDevices = agvolutionSensorIntegrationService.findAll();
        Assertions.assertNotNull(allDevices);
        Assertions.assertFalse(allDevices.isEmpty());
    }

}