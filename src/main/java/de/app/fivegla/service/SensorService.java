package de.app.fivegla.service;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.domain.SensorMasterData;
import de.app.fivegla.domain.SensorType;
import de.app.fivegla.integration.data.SoilScoutData;
import de.app.fivegla.integration.fiware.FiwareIntegrationService;
import de.app.fivegla.integration.parser.SoilScoutParser;
import de.app.fivegla.persistence.SensorMasterDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * Service for sensor data.
 */
@Slf4j
@Service
public class SensorService {

    private final SensorMasterDataRepository sensorMasterDataRepository;
    private final SoilScoutParser soilScoutParser;
    private final FiwareIntegrationService fiwareIntegrationService;

    public SensorService(SensorMasterDataRepository sensorMasterDataRepository,
                         SoilScoutParser soilScoutParser,
                         FiwareIntegrationService fiwareIntegrationService) {
        this.sensorMasterDataRepository = sensorMasterDataRepository;
        this.soilScoutParser = soilScoutParser;
        this.fiwareIntegrationService = fiwareIntegrationService;
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
     * @return the sensor master data
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

    /**
     * Publishes the sensor data.
     *
     * @param sensorId                The sensor id
     * @param base64EncodedSensorData The sensor data
     */
    public void publishSensorData(String sensorId, String base64EncodedSensorData) {
        var sensorMasterData = findById(sensorId);
        var soilScoutData = parse(base64EncodedSensorData, sensorMasterData.getSensorType());
        log.info("Publishing sensor data: {}", soilScoutData);
    }

    private SoilScoutData parse(String sensorData, SensorType sensorType) {
        switch (sensorType) {
            case SOIL_SCOUT -> {
                var decodedSensorData = Base64.getDecoder().decode(sensorData);
                return soilScoutParser.parse(new String(decodedSensorData));
            }
            default -> {
                var errorMessage = ErrorMessage.builder()
                        .error(Error.SENSOR_TYPE_UNKNOWN)
                        .message(String.format("Sensor with the type '%s' does not exist", sensorType)).build();
                throw new BusinessException(errorMessage);
            }
        }
    }

    public void updateSensor(SensorMasterData sensorData) {
        sensorMasterDataRepository.update(sensorData);
    }
}
