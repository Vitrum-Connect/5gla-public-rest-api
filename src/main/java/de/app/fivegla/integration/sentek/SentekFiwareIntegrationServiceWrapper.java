package de.app.fivegla.integration.sentek;


import de.app.fivegla.api.FiwareIdGenerator;
import de.app.fivegla.api.Format;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.integration.sentek.model.csv.Reading;
import de.app.fivegla.integration.sentek.model.xml.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SentekFiwareIntegrationServiceWrapper {
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    public void persist(Logger logger, List<Reading> readings) {
        readings.forEach(reading -> {
            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("V1")
                    .unit("V")
                    .numValue(reading.getV1())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("V2")
                    .unit("V")
                    .numValue(reading.getV2())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A1")
                    .unit("mm")
                    .numValue(reading.getA1())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T1")
                    .unit("°C")
                    .numValue(reading.getT1())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A2")
                    .unit("mm")
                    .numValue(reading.getA2())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T2")
                    .unit("°C")
                    .numValue(reading.getT2())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A3")
                    .unit("mm")
                    .numValue(reading.getA3())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T3")
                    .unit("°C")
                    .numValue(reading.getT3())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A4")
                    .unit("mm")
                    .numValue(reading.getA4())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T4")
                    .unit("°C")
                    .numValue(reading.getT4())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A5")
                    .unit("mm")
                    .numValue(reading.getA5())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T5")
                    .unit("°C")
                    .numValue(reading.getT5())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A6")
                    .unit("mm")
                    .numValue(reading.getA6())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T6")
                    .unit("°C")
                    .numValue(reading.getT6())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A7")
                    .unit("mm")
                    .numValue(reading.getA7())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T7")
                    .unit("°C")
                    .numValue(reading.getT7())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A8")
                    .unit("mm")
                    .numValue(reading.getA8())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T8")
                    .unit("°C")
                    .numValue(reading.getT8())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("A9")
                    .unit("mm")
                    .numValue(reading.getA9())
                    .build());

            deviceMeasurementIntegrationService.persist(createDefaultDeviceMeasurement(logger, reading)
                    .controlledProperty("T9")
                    .unit("°C")
                    .numValue(reading.getT9())
                    .build());
        });
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(Logger logger, Reading reading) {
        log.debug("Persisting sensor data for logger: {}", logger);
        log.debug("Persisting sensor data: {}", reading);
        return DeviceMeasurement.builder()
                .id(FiwareIdGenerator.create(getManufacturerConfiguration(), String.valueOf(logger.getId())))
                .manufacturerSpecificId(String.valueOf(logger.getId()))
                .dateObserved(Format.format(reading.getDateTime()))
                .location(Location.builder()
                        .coordinates(List.of(logger.getLatitude(), logger.getLongitude()))
                        .build());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().sentek();
    }

}
