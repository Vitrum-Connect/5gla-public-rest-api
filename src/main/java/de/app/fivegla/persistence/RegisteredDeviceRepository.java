package de.app.fivegla.persistence;

import de.app.fivegla.persistence.entity.RegisteredDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The repository for the registered devices.
 */
@Repository
public interface RegisteredDeviceRepository extends JpaRepository<RegisteredDevice, Long> {
}
