package de.app.fivegla.persistence;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.domain.SensorMasterData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for sensor master data.
 */
@Component
public class SensorMasterDataRepository {

    private static final List<SensorMasterData> sensorMasterData = new ArrayList<>();

    /**
     * Saves the sensor master data.
     *
     * @param entity The sensor master data.
     */
    public void save(SensorMasterData entity) {
        if (sensorMasterData.stream().filter(sensorMasterData -> sensorMasterData.getSensorId().equals(entity.getSensorId())).findFirst().isEmpty()) {
            sensorMasterData.add(entity);
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
        return sensorMasterData;
    }

    public SensorMasterData findById(String id) {
        return sensorMasterData.stream().filter(sensorMasterData -> sensorMasterData.getSensorId().equals(id)).findFirst().orElseThrow(() -> {
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
        sensorMasterData.removeIf(sensorMasterData -> sensorMasterData.getSensorId().equals(id));
    }
}
