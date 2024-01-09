package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.soilscout.cache.SoilScoutSensorCache;
import de.app.fivegla.integration.soilscout.model.Sensor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Soil Scout integration service to access devices and sensor data.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SoilScoutSensorIntegrationService extends AbstractIntegrationService {

    private final SoilScoutSensorCache soilScoutSensorCache;

    /**
     * Fetches all sensors from the SoilScout API.
     *
     * @return List of sensors.
     */
    public List<Sensor> fetchAll() {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(getAccessToken());
        var httpEntity = new HttpEntity<String>(headers);
        var response = restTemplate.exchange(url + "/devices/", HttpMethod.GET, httpEntity, Sensor[].class);
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
    public Sensor fetch(int id) {
        if (soilScoutSensorCache.get(id).isPresent()) {
            return soilScoutSensorCache.get(id).get();
        } else {
            return getSensor(id, getAccessToken());
        }
    }

    private Sensor getSensor(int id, String access) {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(access);
        var httpEntity = new HttpEntity<String>(headers);
        try {
            var response = restTemplate.exchange(url + "/devices/" + id, HttpMethod.GET, httpEntity, Sensor.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                var sensor = Objects.requireNonNull(response.getBody());
                soilScoutSensorCache.put(sensor);
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
