package de.app.fivegla.integration.sentek;


import de.app.fivegla.api.enums.MeasurementType;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.fiware.model.internal.NumberAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
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
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("V1"),
                            new NumberAttribute(reading.getV1()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("V2"),
                            new NumberAttribute(reading.getV2()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A1"),
                            new NumberAttribute(reading.getA1()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T1"),
                            new NumberAttribute(reading.getT1()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A2"),
                            new NumberAttribute(reading.getA2()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T2"),
                            new NumberAttribute(reading.getT2()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A3"),
                            new NumberAttribute(reading.getA3()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T3"),
                            new NumberAttribute(reading.getT3()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A4"),
                            new NumberAttribute(reading.getA4()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T4"),
                            new NumberAttribute(reading.getT4()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A5"),
                            new NumberAttribute(reading.getA5()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T5"),
                            new NumberAttribute(reading.getT5()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A6"),
                            new NumberAttribute(reading.getA6()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T6"),
                            new NumberAttribute(reading.getT6()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A7"),
                            new NumberAttribute(reading.getA7()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T7"),
                            new NumberAttribute(reading.getT7()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A8"),
                            new NumberAttribute(reading.getA8()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T8"),
                            new NumberAttribute(reading.getT8()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("A9"),
                            new NumberAttribute(reading.getA9()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));

            deviceMeasurementIntegrationService.persist(
                    new DeviceMeasurement(
                            getManufacturerConfiguration().fiwarePrefix() + logger.getLoggerId(),
                            MeasurementType.SENTEK_SENSOR.name(),
                            new TextAttribute("T9"),
                            new NumberAttribute(reading.getT9()),
                            new DateTimeAttribute(reading.getDateTime().toInstant()),
                            new EmptyAttribute(),
                            latitude,
                            longitude));
        });
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().sentek();
    }

}
