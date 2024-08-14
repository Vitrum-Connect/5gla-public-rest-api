package de.app.fivegla.business;

import de.app.fivegla.persistence.RegisteredDeviceRepository;
import de.app.fivegla.persistence.entity.RegisteredDevice;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisteredDevicesService {

    private final GroupService groupService;
    private final RegisteredDeviceRepository registeredDeviceRepository;

    /**
     * Registers a device.
     *
     * @param prefilledEntity The prefilled device entity to register.
     * @return The registered device entity.
     */
    public RegisteredDevice registerDevice(RegisteredDevice prefilledEntity, String groupId) {
        log.info("Registering device: {}", prefilledEntity);
        prefilledEntity.setOid(UUID.randomUUID().toString());
        groupService.getOrDefault(prefilledEntity.getTenant(), groupId);
        return registeredDeviceRepository.save(prefilledEntity);
    }

    /**
     * Finds all registered devices for a tenant.
     *
     * @param tenant The tenant to find the registered devices for.
     * @return The list of registered devices.
     */
    public List<RegisteredDevice> findAll(Tenant tenant) {
        log.info("Finding all registered devices for tenant: {}", tenant);
        return registeredDeviceRepository.findAllByTenant(tenant);
    }

    /**
     * Finds a registered device by tenant and sensor ID.
     *
     * @param tenant   The tenant to find the registered device for.
     * @param sensorId The sensor ID to find the registered device for.
     * @return The registered device.
     */
    public Optional<RegisteredDevice> findByTenantAndSensorId(Tenant tenant, String sensorId) {
        log.info("Finding registered device for tenant {} and sensor ID {}.", tenant, sensorjsonId);
        return registeredDeviceRepository.findByTenantAndOid(tenant, sensorId);
    }
}
