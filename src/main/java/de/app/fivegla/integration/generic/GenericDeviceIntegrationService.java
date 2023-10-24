package de.app.fivegla.integration.generic;

import de.app.fivegla.api.FiwareDeviceId;
import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.config.ApplicationConfiguration;
import de.app.fivegla.config.manufacturer.CommonManufacturerConfiguration;
import de.app.fivegla.fiware.DeviceIntegrationService;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.Location;
import de.app.fivegla.monitoring.FiwareEntityMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class represents a service for integrating with devices.
 */
@Slf4j
@Service
public class GenericDeviceIntegrationService {

    private final DeviceIntegrationService deviceIntegrationService;
    private final FiwareEntityMonitor fiwareEntityMonitor;
    private final ApplicationConfiguration applicationConfiguration;

    public GenericDeviceIntegrationService(DeviceIntegrationService deviceIntegrationService,
                                           FiwareEntityMonitor fiwareEntityMonitor,
                                           ApplicationConfiguration applicationConfiguration) {
        this.deviceIntegrationService = deviceIntegrationService;
        this.fiwareEntityMonitor = fiwareEntityMonitor;
        this.applicationConfiguration = applicationConfiguration;
    }

    /**
     * Registers a device for a specific manufacturer with the given ID and coordinates.
     * The device is created using the manufacturer configuration, ID, and coordinates provided.
     * It is then persisted using the deviceIntegrationService, and the fiwareEntityMonitor is
     * notified of the device registration.
     *
     * @param manufacturer The manufacturer of the device.
     * @param id           The ID of the device.
     * @param latitude     The latitude coordinate of the device's location.
     * @param longitude    The longitude coordinate of the device's location.
     */
    public void persist(Manufacturer manufacturer, String id, double latitude, double longitude) {
        var manufacturerConfiguration = getManufacturerConfiguration(manufacturer);
        var location = Location.builder()
                .coordinates(List.of(latitude, longitude))
                .build();
        var device = Device.builder()
                .id(FiwareDeviceId.create(manufacturerConfiguration, id))
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(manufacturerConfiguration.getKey()))
                        .build())
                .location(location)
                .build();
        deviceIntegrationService.persist(device);
        fiwareEntityMonitor.deviceRegistered(manufacturer, 1);
    }

    private CommonManufacturerConfiguration getManufacturerConfiguration(Manufacturer manufacturer) {
        return switch (manufacturer) {
            case SENTEK -> applicationConfiguration.getSensors().sentek();
            case FARM21 -> applicationConfiguration.getSensors().farm21();
            case SOILSCOUT -> applicationConfiguration.getSensors().soilscout();
            case AGVOLUTION -> applicationConfiguration.getSensors().agvolution();
            case AGRANIMO -> applicationConfiguration.getSensors().agranimo();
            case WEENAT -> applicationConfiguration.getSensors().weenat();
            case SENSOTERRA -> applicationConfiguration.getSensors().sensoterra();
            case MICA_SENSE -> applicationConfiguration.getSensors().micasense();
        };
    }

    /**
     * Deletes a device with the specified ID.
     *
     * @param id The ID of the device to be deleted.
     */
    public void delete(String id) {
        deviceIntegrationService.delete(id);
    }
}
