package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.soilscout.cache.SensorCache;
import de.app.fivegla.integration.soilscout.model.SoilScoutSensor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Soil Scout integration service to access devices and sensor data.
 */
@Slf4j
@Service
public class SoilScoutSensorIntegrationService extends AbstractSoilScoutIntegrationService {

    private final SensorCache sensorCache;

    public SoilScoutSensorIntegrationService(SensorCache sensorCache) {
        this.sensorCache = sensorCache;
    }

    /**
     * Fetches all sensors from the SoilScout API.
     *
     * @return List of sensors.
     */
    public List<SoilScoutSensor> findAll() {
        return getSensors(getAccessToken());
    }

    private List<SoilScoutSensor> getSensors(String accessToken) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken);
        var httpEntity = new HttpEntity<String>(headers);
        var response = restTemplate.exchange(url + "/devices/", HttpMethod.GET, httpEntity, SoilScoutSensor[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch devices for SoilScout API.").build();
            throw new BusinessException(errorMessage);
        }
    }

    /**
     * Fetches a single sensor from the SoilScout API.
     *
     * @param id The id of the sensor.
     * @return The sensor.
     */
    public SoilScoutSensor find(int id) {
        if (sensorCache.get(id).isPresent()) {
            return sensorCache.get(id).get();
        } else {
            return getSensor(id, getAccessToken());
        }
    }

    private SoilScoutSensor getSensor(int id, String access) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(access);
        var httpEntity = new HttpEntity<String>(headers);
        try {
            var response = restTemplate.exchange(url + "/devices/" + id, HttpMethod.GET, httpEntity, SoilScoutSensor.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                var sensor = Objects.requireNonNull(response.getBody());
                sensorCache.put(sensor);
                return sensor;
            } else {
                var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch device for SoilScout API.").build();
                throw new BusinessException(errorMessage);
            }
        } catch (HttpClientErrorException e) {
            var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch device for SoilScout API.").build();
            throw new BusinessException(errorMessage);
        }
    }
}
