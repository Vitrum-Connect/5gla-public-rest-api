package de.app.fivegla.integration.sentek;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
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
        var latitude = logger.getLatitude();
        var longitude = logger.getLongitude();
        readings.forEach(reading -> {
            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("V1",
                                    FiwareType.TEXT,
                                    reading.getV1(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("V2",
                                    FiwareType.TEXT,
                                    reading.getV2(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A1",
                                    FiwareType.TEXT,
                                    reading.getA1(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T1",
                                    FiwareType.TEXT,
                                    reading.getT1(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A2",
                                    FiwareType.TEXT,
                                    reading.getA2(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T2",
                                    FiwareType.TEXT,
                                    reading.getT2(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A3",
                                    FiwareType.TEXT,
                                    reading.getA3(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T3",
                                    FiwareType.TEXT,
                                    reading.getT3(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A4",
                                    FiwareType.TEXT,
                                    reading.getA4(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T4",
                                    FiwareType.TEXT,
                                    reading.getT4(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A5",
                                    FiwareType.TEXT,
                                    reading.getA5(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T5",
                                    FiwareType.TEXT,
                                    reading.getT5(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A6",
                                    FiwareType.TEXT,
                                    reading.getA6(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T6",
                                    FiwareType.TEXT,
                                    reading.getT6(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A7",
                                    FiwareType.TEXT,
                                    reading.getA7(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T7",
                                    FiwareType.TEXT,
                                    reading.getT7(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A8",
                                    FiwareType.TEXT,
                                    reading.getA8(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T8",
                                    FiwareType.TEXT,
                                    reading.getT8(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A9",
                                    FiwareType.TEXT,
                                    reading.getA9(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T9",
                                    FiwareType.TEXT,
                                    reading.getT9(),
                                    reading.getDateTime().toInstant(),
                                    latitude,
                                    longitude)
                            .build());
        });
    }

    private DeviceMeasurementBuilder defaultMeasurement(Logger logger, Reading reading) {
        log.debug("Persisting sensor data for logger: {}", logger);
        log.debug("Persisting sensor data: {}", reading);
        var builder = new DeviceMeasurementBuilder();
        return builder.withId(getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId())
                .withType(MeasurementType.SENTEK_SENSOR.name());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().sentek();
    }

}
