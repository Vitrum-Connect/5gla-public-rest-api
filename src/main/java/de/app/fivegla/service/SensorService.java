package de.app.fivegla.service;

import de.app.fivegla.domain.SensorMasterData;
import de.app.fivegla.persistence.SensorMasterDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for sensor data.
 */
@Slf4j
@Service
public class SensorService {

    private final SensorMasterDataRepository sensorMasterDataRepository;

    public SensorService(SensorMasterDataRepository sensorMasterDataRepository) {
        this.sensorMasterDataRepository = sensorMasterDataRepository;
    }

    /**
     * Creates a new sensor.
     */
    public void createSensor(SensorMasterData sensorMasterData) {
        log.debug("Creating sensor");
        sensorMasterDataRepository.save(sensorMasterData);
    }

    /**
     * Returns all sensor master data.
     *
     * @return the sensor master data
     */
    public List<SensorMasterData> findAll() {
        return sensorMasterDataRepository.findAll();
    }

    /**
     * Returns the sensor master data by id.
     *
     * @param id the sensor id
     * @return
     */
    public SensorMasterData findById(String id) {
        return sensorMasterDataRepository.findById(id);
    }

    /**
     * Deletes the sensor master data by id.
     *
     * @param id the sensor id
     */
    public void delete(String id) {
        sensorMasterDataRepository.delete(id);
    }
}
