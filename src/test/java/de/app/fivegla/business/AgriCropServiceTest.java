package de.app.fivegla.business;

import de.app.fivegla.SpringBootIntegrationTestBase;
import de.app.fivegla.persistence.entity.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AgriCropServiceTest extends SpringBootIntegrationTestBase {

    @Autowired
    private AgriCropService agriCropService;

    @SuppressWarnings("FieldCanBeLocal")
    private final String feature = """
            {
                "type": "FeatureCollection",
                "features": [
                  {
                    "type": "Feature",
                    "properties": {},
                    "geometry": {
                      "coordinates": [
                        [
                          [
                            10.443273285881673,
                            52.88334609790465
                          ],
                          [
                            10.437440177134079,
                            52.87646841445812
                          ],
                          [
                            10.440236095438934,
                            52.873898638877165
                          ],
                          [
                            10.443741637678897,
                            52.87545765422925
                          ],
                          [
                            10.446778828121694,
                            52.87920077546457
                          ],
                          [
                            10.443273285881673,
                            52.88334609790465
                          ]
                        ]
                      ],
                      "type": "Polygon"
                    }
                  }
                ]
              }""";

    @SuppressWarnings("FieldCanBeLocal")
    private final String csv = """
                            10.443273285881673,52.88334609790465
                            10.437440177134079,52.87646841445812
                            10.440236095438934,52.873898638877165
                            10.443741637678897,52.87545765422925
                            10.446778828121694,52.87920077546457
                            10.443273285881673,52.88334609790465
            """;

    @Test
    void givenValidFeatureWhenParsingThenTheServiceShouldReturnTheSimpleFeature() {
        var parsedFeature = agriCropService.createFromGeoJson(new Tenant(), "some-random-crop-id", feature);
        assertThat(parsedFeature).isNotNull();
    }

    @Test
    void givenValidCsvWhenParsingThenTheServiceShouldReturnTheSimpleFeature() {
        var parsedFeature = agriCropService.createFromCsv(new Tenant(), "another-random-crop-id", csv);
        assertThat(parsedFeature).isNotNull();
    }

}
