package de.app.fivegla.persistence;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.domain.SensorMasterData;
import de.app.fivegla.persistence.root.SensorMasterDataRoot;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Repository for sensor master data.
 */
@Component
public class SensorMasterDataRepository {

    private final EmbeddedStorageManager storageManager;

    public SensorMasterDataRepository(EmbeddedStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    private SensorMasterDataRoot root() {
        return (SensorMasterDataRoot) storageManager.root();
    }

    /**
     * Saves the sensor master data.
     *
     * @param entity The sensor master data.
     */
    public void save(SensorMasterData entity) {
        if (root().getSensorMasterData().stream().filter(sensorMasterData -> sensorMasterData.getSensorId().equals(entity.getSensorId())).findFirst().isEmpty()) {
            root().getSensorMasterData().add(entity);
            storageManager.storeRoot();
        } else {
            var errorMessage = ErrorMessage.builder()
                    .error(Error.SENSOR_ALREADY_EXISTS)
                    .message(String.format("Sensor with the id '%s' does already exists", entity.getSensorId())).build();
            throw new BusinessException(errorMessage);
        }
    }

    /**
     * Returns all sensor master data.
     *
     * @return the sensor master data
     */
    public List<SensorMasterData> findAll() {
        return root().getSensorMasterData();
    }

    public SensorMasterData findById(String id) {
        return root().getSensorMasterData().stream().filter(sensorMasterData -> sensorMasterData.getSensorId().equals(id)).findFirst().orElseThrow(() -> {
            var errorMessage = ErrorMessage.builder()
                    .error(Error.SENSOR_NOT_FOUND)
                    .message(String.format("Sensor with the id '%s' does not exists", id)).build();
            throw new BusinessException(errorMessage);
        });
    }

    /**
     * Deletes the sensor master data by id.
     *
     * @param id the sensor id
     */
    public void delete(String id) {
        root().getSensorMasterData().removeIf(sensorMasterData -> sensorMasterData.getSensorId().equals(id));
        storageManager.storeRoot();
    }
}
