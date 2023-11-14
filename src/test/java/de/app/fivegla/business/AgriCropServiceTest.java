package de.app.fivegla.business;

import de.app.fivegla.SpringBootIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AgriCropServiceTest extends SpringBootIntegrationTestBase {

    @Autowired
    private AgriCropService agriCropService;

    private final String feature = "{\n" +
            "    \"type\": \"Feature\",\n" +
            "    \"properties\": {\n" +
            "        \"name\": \"Coors Field\",\n" +
            "        \"amenity\": \"Baseball Stadium\",\n" +
            "        \"popupContent\": \"This is where the Rockies play!\"\n" +
            "    },\n" +
            "    \"geometry\": {\n" +
            "        \"type\": \"Point\",\n" +
            "        \"coordinates\": [-104.99404, 39.75621]\n" +
            "    }\n" +
            "}";

    @Test
    void givenValidFeatureWhenParsingThenTheServiceShouldReturnTheSimpleFeature() {
        var parsedFeature = agriCropService.parseFeature(feature);
        assertThat(parsedFeature).isNotNull();
        assertThat(parsedFeature.getAttribute("name")).isEqualTo("Coors Field");
        assertThat(parsedFeature.getAttribute("amenity")).isEqualTo("Baseball Stadium");
        assertThat(parsedFeature.getAttribute("popupContent")).isEqualTo("This is where the Rockies play!");
    }

}
