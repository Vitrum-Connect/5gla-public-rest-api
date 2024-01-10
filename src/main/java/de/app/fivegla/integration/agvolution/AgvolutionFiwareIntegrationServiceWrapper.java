package de.app.fivegla.integration.agvolution;


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
import de.app.fivegla.integration.agvolution.model.SeriesEntry;
import de.app.fivegla.integration.agvolution.model.TimeSeriesEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AgvolutionFiwareIntegrationServiceWrapper {
    private final DeviceIntegrationService deviceIntegrationService;
    private final DeviceMeasurementIntegrationService deviceMeasurementIntegrationService;
    private final ApplicationConfiguration applicationConfiguration;

    public void persist(SeriesEntry seriesEntry) {
        persist(seriesEntry.getDeviceId(), seriesEntry.getLatitude(), seriesEntry.getLongitude());
        seriesEntry.getTimeSeriesEntries().forEach(timeSeriesEntry -> {
            var deviceMeasurements = createDeviceMeasurements(seriesEntry, timeSeriesEntry);
            log.info("Persisting measurement for device: {}", seriesEntry.getDeviceId());
            deviceMeasurements.forEach(deviceMeasurement -> {
                log.info("Persisting measurement: {}", deviceMeasurement);
                deviceMeasurementIntegrationService.persist(deviceMeasurement);
            });
        });
    }

    private void persist(String deviceId, double latitude, double longitude) {
        var device = Device.builder()
                .id(FiwareDeviceId.create(getManufacturerConfiguration(), deviceId))
                .manufacturerSpecificId(deviceId)
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(getManufacturerConfiguration().getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(latitude, longitude))
                        .build())
                .build();
        deviceIntegrationService.persist(device);
    }

    private List<DeviceMeasurement> createDeviceMeasurements(SeriesEntry seriesEntry, TimeSeriesEntry timeSeriesEntry) {
        log.debug("Persisting data for device: {}", seriesEntry.getDeviceId());
        log.debug("Persisting data: {}", timeSeriesEntry);
        var deviceMeasurements = new ArrayList<DeviceMeasurement>();
        timeSeriesEntry.getValues().forEach(timeSeriesValue -> {
            var deviceMeasurement = DeviceMeasurement.builder()
                    .id(FiwareDevicMeasurementeId.create(getManufacturerConfiguration()))
                    .refDevice(FiwareDeviceId.create(getManufacturerConfiguration(), String.valueOf(seriesEntry.getDeviceId())))
                    .dateObserved(Format.format(timeSeriesValue.getTime()))
                    .location(Location.builder()
                            .coordinates(List.of(seriesEntry.getLatitude(), seriesEntry.getLongitude()))
                            .build())
                    .controlledProperty(timeSeriesEntry.getKey())
                    .numValue(timeSeriesValue.getValue())
                    .unit(timeSeriesEntry.getUnit())
                    .build();
            deviceMeasurements.add(deviceMeasurement);
        });
        return deviceMeasurements;
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration() {
        return applicationConfiguration.getSensors().agvolution();
    }

    /**
     * Retrieves the unique device ID of the specified device.
     *
     * @param deviceId The unique identifier of the device.
     * @return The device ID.
     */
    public String deviceIdOf(String deviceId) {
        return FiwareDeviceId.create(getManufacturerConfiguration(), deviceId);
    }
}
