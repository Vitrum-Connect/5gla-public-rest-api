package de.app.fivegla.integration.fiware;


import de.app.fivegla.api.Constants;
import de.app.fivegla.fiware.FiwareIntegrationService;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceCategoryValues;
import de.app.fivegla.integration.soilscout.model.SoilScoutSensor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Service
public class FiwareIntegrationServiceWrapper {

    @Value("${fiware.context-broker-url}")
    private String contextBrokerUrl;

    /**
     * Create a new soil scout sensor in FIWARE.
     *
     * @param sensor the sensor to create
     */
    public void createSensor(SoilScoutSensor sensor) {
        var device = Device.builder()
                .id(getFiwareId(sensor))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .build();
        fiwareIntegrationService().persist(device);
    }

    private static String getFiwareId(SoilScoutSensor sensor) {
        return Constants.FIWARE_SOIL_SCOUT_SENSOR_ID_PREFIX + sensor.getId();
    }

    private FiwareIntegrationService fiwareIntegrationService() {
        return new FiwareIntegrationService(contextBrokerUrl);
    }

}
