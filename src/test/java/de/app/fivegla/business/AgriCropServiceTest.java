package de.app.fivegla.business;

import de.app.fivegla.integration.agricrop.AgriCropFiwareIntegrationServiceWrapper;
import de.app.fivegla.persistence.entity.Tenant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AgriCropServiceTest {


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


    private AgriCropService agriCropService;

    private AutoCloseable openMocks;

    @Mock
    private AgriCropFiwareIntegrationServiceWrapper agriCropFiwareIntegrationServiceWrapper;


    public AgriCropServiceTest() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.agriCropService = new AgriCropService(agriCropFiwareIntegrationServiceWrapper);
    }

    @Test
    void givenValidCsvWhenParsingThenTheServiceShouldReturnTheSimpleFeature() {
        var tenant = new Tenant();
        tenant.setTenantId("another-random-tenant-id");
        Assertions.assertDoesNotThrow(() -> agriCropService.createFromCsv(tenant, "another-random-crop-id", csv));
    }

}
