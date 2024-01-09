package de.app.fivegla.integration.sentek;


import de.app.fivegla.api.FiwareDevicMeasurementeId;
import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.Format;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.DeviceMeasurementIntegrationService;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.integration.sentek.model.csv.Reading;
import de.app.fivegla.integration.sentek.model.xml.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
public class SentekFiwareIntegrationServiceWrapper {
    private final DeviceIntegrationService deviceIntegrationService;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    public SentekFiwareIntegrationServiceWrapper(DeviceIntegrationService deviceIntegrationService,
                                                 DeviceMeasurementIntegrationService deviceMeasurementIntegrationService,
                                                 ApplicationConfiguration applicationConfiguration) {
        this.deviceIntegrationService = deviceIntegrationService;
        this.deviceMeasurementIntegrationService = deviceMeasurementIntegrationService;
        this.applicationConfiguration = applicationConfiguration;
    }

    public void persist(Logger logger, List<Reading> readings) {
        try {
            persist(logger);
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
        } catch (RuntimeException e) {
            log.error("Error while persisting data for logger: {}", logger.getId(), e);
        }
    }

    private void persist(Logger logger) {
        var device = Device.builder()
                .id(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(logger.getId())))
                .manufacturerSpecificId(String.valueOf(logger.getId()))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(getManufacturerConfiguration().getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(logger.getLatitude(), logger.getLongitude()))
                        .build())
                .build();
        deviceIntegrationService.persist(device);
    }

    private DeviceMeasurement.DeviceMeasurementBuilder createDefaultDeviceMeasurement(Logger logger, Reading reading) {
        log.debug("Persisting sensor data for logger: {}", logger);
        log.debug("Persisting sensor data: {}", reading);
        return DeviceMeasurement.builder()
                .id(FiwareDevicMeasurementeId.create(getManufacturerConfiguration()))
                .refDevice(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(logger.getId())))
                .dateObserved(Format.format(reading.getDateTime()))
                .location(Location.builder()
                        .coordinates(List.of(logger.getLatitude(), logger.getLongitude()))
                        .build());
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().sentek();
    }

    /**
     * Persist the device readings to the logger.
     *
     * @param device   The device object containing the ID and location coordinates.
     * @param readings The list of readings to persist.
     */
    public void persist(Device device, List<Reading> readings) {
        var logger = new Logger();
        logger.setId(Integer.parseInt(device.getManufacturerSpecificId()));
        logger.setLatitude(device.getLocation().getCoordinates().get(0));
        logger.setLongitude(device.getLocation().getCoordinates().get(1));
        persist(logger, readings);
    }

    /**
     * Retrieves the device ID for a given sensor ID.
     *
     * @param sensorId the ID of the sensor
     * @return the device ID associated with the sensor ID
     */
    public String deviceIdOf(int sensorId) {
        return FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(sensorId));
    }
}
