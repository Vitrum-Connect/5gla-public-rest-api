package de.app.fivegla.integration.sentek;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.api.FiwareTypes;
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
        readings.forEach(reading -> {
            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("V1",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getV1()),
                                    reading.getDateTime().toInstant(),
                                    "V1")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("V2",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getV2()),
                                    reading.getDateTime().toInstant(),
                                    "V2")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A1",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA1()),
                                    reading.getDateTime().toInstant(),
                                    "A1")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T1",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT1()),
                                    reading.getDateTime().toInstant(),
                                    "T1")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A2",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA2()),
                                    reading.getDateTime().toInstant(),
                                    "A2")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T2",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT2()),
                                    reading.getDateTime().toInstant(),
                                    "T2")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A3",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA3()),
                                    reading.getDateTime().toInstant(),
                                    "A3")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T3",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT3()),
                                    reading.getDateTime().toInstant(),
                                    "T3")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A4",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA4()),
                                    reading.getDateTime().toInstant(),
                                    "A4")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T4",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT4()),
                                    reading.getDateTime().toInstant(),
                                    "T4")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A5",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA5()),
                                    reading.getDateTime().toInstant(),
                                    "A5")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T5",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT5()),
                                    reading.getDateTime().toInstant(),
                                    "T5")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A6",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA6()),
                                    reading.getDateTime().toInstant(),
                                    "A6")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T6",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT6()),
                                    reading.getDateTime().toInstant(),
                                    "T6")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A7",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA7()),
                                    reading.getDateTime().toInstant(),
                                    "A7")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T7",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT7()),
                                    reading.getDateTime().toInstant(),
                                    "T7")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A8",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA8()),
                                    reading.getDateTime().toInstant(),
                                    "A8")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T8",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT8()),
                                    reading.getDateTime().toInstant(),
                                    "T8")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("A9",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getA9()),
                                    reading.getDateTime().toInstant(),
                                    "A9")
                            .build());

            deviceMeasurementIntegrationService.persist(
                    defaultMeasurement(logger, reading)
                            .withMeasurement("T9",
                                    FiwareTypes.TEXT.getKey(),
                                    String.valueOf(reading.getT9()),
                                    reading.getDateTime().toInstant(),
                                    "T9")
                            .build());
        });
    }

    private DeviceMeasurementBuilder defaultMeasurement(Logger logger, Reading reading) {
        log.debug("Persisting sensor data for logger: {}", logger);
        log.debug("Persisting sensor data: {}", reading);
        var builder = new DeviceMeasurementBuilder();
        return builder.withId(getManufacturerConfiguration().fiwareDeviceIdPrefix() + ":" + logger.getLoggerId())
                .withType(MeasurementType.SENTEK_SENSOR.name())
                .withLocation(logger.getLatitude(), logger.getLongitude());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().sentek();
    }

}
