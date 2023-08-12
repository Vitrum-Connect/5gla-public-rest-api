package de.app.fivegla.integration.agvolution;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AgvolutionSensorIntegrationServiceIT extends SpringBootIntegrationTestBase {

    @Autowired
    private AgvolutionSensorIntegrationService agvolutionSensorIntegrationService;

    @Test
    void givenValidCredentialsWhenGetDevicesThenTheRequestShouldBeAccepted() {
        var allDevices = agvolutionSensorIntegrationService.findAll();
        assertThat(allDevices).isNotNull();
        assertThat(allDevices).isNotEmpty();
    }

}