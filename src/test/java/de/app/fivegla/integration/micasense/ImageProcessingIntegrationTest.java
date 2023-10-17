package de.app.fivegla.integration.micasense;


import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.integration.micasense.model.MicaSenseChannel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageProcessingIntegrationTest extends SpringBootIntegrationTestBase {

    @Autowired
    private MicaSenseIntegrationService micaSenseIntegrationService;

    @Test
    void givenValidImageWhenReadingImageInformationThen() throws Throwable {
        micaSenseIntegrationService.processImage("transactionId", "droneId", MicaSenseChannel.BLUE, readBase64Image("base64_encoded_drone_image_1.txt"));
        micaSenseIntegrationService.processImage("transactionId", "droneId", MicaSenseChannel.BLUE, readBase64Image("base64_encoded_drone_image_2.txt"));
        micaSenseIntegrationService.processImage("transactionId", "droneId", MicaSenseChannel.BLUE, readBase64Image("base64_encoded_drone_image_3.txt"));
        micaSenseIntegrationService.processImage("transactionId", "droneId", MicaSenseChannel.BLUE, readBase64Image("base64_encoded_drone_image_4.txt"));
        micaSenseIntegrationService.processImage("transactionId", "droneId", MicaSenseChannel.BLUE, readBase64Image("base64_encoded_drone_image_5.txt"));
    }

    private String readBase64Image(String fileName) throws Throwable {
        return new String(this.getClass().getClassLoader().getResourceAsStream("micasense/encoded_image_set/" + fileName).readAllBytes());
    }

}
