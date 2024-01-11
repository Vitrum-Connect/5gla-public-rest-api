package de.app.fivegla.integration.farm21;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Format;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.farm21.dto.response.SensorDataResponse;
import de.app.fivegla.integration.farm21.model.Sensor;
import de.app.fivegla.integration.farm21.model.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.*;

/**
 * Service to read the sensor data from the API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class Farm21SensorDataIntegrationService extends AbstractIntegrationService {

    private final Farm21SensorIntegrationService farm21SensorIntegrationService;
    private final RestTemplate restTemplate;

    /**
     * Fetches all sensor data for all sensors from the Farm21 API.
     *
     * @param since Start date.
     * @param until End date.
     * @return Map of sensors with sensor data.
     */
    public Map<Sensor, List<SensorData>> fetchAll(Instant since, Instant until) {
        try {
            var sensors = farm21SensorIntegrationService.fetchAll();
            log.debug("Found {} sensors", sensors.size());
            Map<Sensor, List<SensorData>> sensorsWithSensorData = new HashMap<>();
            sensors.forEach(sensor -> {
                log.debug("Processing sensor {}", sensor.getId());
                var sensorData = fetchAllForSensor(sensor.getId(), since, until);
                log.debug("Found {} sensor data for sensor {}", sensorData.size(), sensor.getId());
                if (!sensorData.isEmpty()) {
                    sensorsWithSensorData.put(sensor, sensorData);
                } else {
                    log.warn("No sensor data found for sensor {}", sensor.getId());
                }
            });
            return sensorsWithSensorData;
        } catch (RuntimeException e) {
            var errorMessage = ErrorMessage.builder().error(Error.FARM21_COULD_NOT_FETCH_DEVICE_DATA).message("Could not fetch devices for Farm21 API.").build();
            log.error(errorMessage.asDetail());
        }
        log.warn("Could not fetch devices for Farm21 API, therefore returning empty map of device data.");
        return Collections.emptyMap();
    }

    private List<SensorData> fetchAllForSensor(int sensorId, Instant since, Instant until) {
        try {
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(getAccessToken());
            var httpEntity = new HttpEntity<String>(headers);
            var uri = UriComponentsBuilder.fromHttpUrl(url + "/sensors/{id}/data?start_date={since}&end_date={until}")
                    .encode()
                    .toUriString();
            uri += "&sensor_data[]=soil_moisture_10" +
                    "&sensor_data[]=soil_moisture_20" +
                    "&sensor_data[]=soil_moisture_30" +
                    "&sensor_data[]=temp_neg_10" +
                    "&sensor_data[]=humidity" +
                    "&sensor_data[]=temp_pos_10" +
                    "&sensor_data[]=crop_type" +
                    "&sensor_data[]=race_type" +
                    "&sensor_data[]=cultivation_type" +
                    "&sensor_data[]=latitude" +
                    "&sensor_data[]=longitude" +
                    "&sensor_data[]=battery";
            log.debug("Fetching sensor data from URI: {}", uri);
            var uriVariables = Map.of("id",
                    sensorId,
                    "since",
                    Format.format(since),
                    "until",
                    Format.format(until));
            log.debug("URI variables: {}", uriVariables);
            var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, SensorDataResponse.class, uriVariables);
            if (response.getStatusCode().is2xxSuccessful()) {
                return Objects.requireNonNull(response.getBody()).getSensorData();
            } else {
                var errorMessage = ErrorMessage.builder().error(Error.FARM21_COULD_NOT_AUTHENTICATE).message("Could not fetch devices for Farm21 API.").build();
                throw new BusinessException(errorMessage);
            }
        } catch (HttpServerErrorException e) {
            log.debug("Could not fetch sensor data for sensor {} from Farm21 API.", sensorId);
            log.error("Error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
