package de.app.fivegla.integration.agranimo;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
import de.app.fivegla.integration.agranimo.model.SoilMoisture;
import de.app.fivegla.integration.agranimo.model.Zone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AgranimoFiwareIntegrationServiceWrapper {
    private final ApplicationConfiguration applicationConfiguration;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;

    /**
     * Persists the soil moisture measurement for a given zone.
     *
     * @param zone         the zone associated with the soil moisture measurement
     * @param soilMoisture the soil moisture measurement to persist
     */
    public void persist(Zone zone, SoilMoisture soilMoisture) {
        var smo1 = defaultDeviceMeasurement(zone, soilMoisture)
                .withMeasurement("smo1",
                        FiwareType.TEXT,
                        soilMoisture.getSmo1(),
                        soilMoisture.getTms(),
                        zone.getData().getPoint().getCoordinates()[0],
                        zone.getData().getPoint().getCoordinates()[1])
                .build();
        log.info("Persisting soil moisture 10: {}", smo1);
        deviceMeasurementIntegrationService.persist(smo1);

        var smo2 = defaultDeviceMeasurement(zone, soilMoisture)
                .withMeasurement("smo2",
                        FiwareType.TEXT,
                        soilMoisture.getSmo2(),
                        soilMoisture.getTms(),
                        zone.getData().getPoint().getCoordinates()[0],
                        zone.getData().getPoint().getCoordinates()[1])
                .build();
        log.info("Persisting soil moisture 20: {}", smo2);
        deviceMeasurementIntegrationService.persist(smo2);

        var smo3 = defaultDeviceMeasurement(zone, soilMoisture)
                .withMeasurement("smo3",
                        FiwareType.TEXT,
                        soilMoisture.getSmo3(),
                        soilMoisture.getTms(),
                        zone.getData().getPoint().getCoordinates()[0],
                        zone.getData().getPoint().getCoordinates()[1])
                .build();
        log.info("Persisting soil moisture 30: {}", smo3);
        deviceMeasurementIntegrationService.persist(smo3);

        var smo4 = defaultDeviceMeasurement(zone, soilMoisture)
                .withMeasurement("smo4",
                        FiwareType.TEXT,
                        soilMoisture.getSmo4(),
                        soilMoisture.getTms(),
                        zone.getData().getPoint().getCoordinates()[0],
                        zone.getData().getPoint().getCoordinates()[1])
                .build();
        log.info("Persisting soil moisture 40: {}", smo4);
        deviceMeasurementIntegrationService.persist(smo4);
    }

    private DeviceMeasurementBuilder defaultDeviceMeasurement(Zone zone, SoilMoisture soilMoisture) {
        log.debug("Persisting data for zone: {}", zone.getId());
        return new DeviceMeasurementBuilder()
                .withId(getManufacturerConfiguration().fiwarePrefix() + soilMoisture.getDeviceId())
                .withType(MeasurementType.AGRANIMO_SENSOR.name());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().agranimo();
    }

}
