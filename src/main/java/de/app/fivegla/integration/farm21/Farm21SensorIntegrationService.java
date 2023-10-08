package de.app.fivegla.integration.farm21;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.integration.farm21.model.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Service for the Farm21 API.
 */
@Slf4j
@Service
public class Farm21SensorIntegrationService extends AbstractIntegrationService {

    private final RestTemplate restTemplate;

    Farm21SensorIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches all sensors from the SoilScout API.
     *
     * @return List of sensors.
     */
    public List<Sensor> fetchAll() {
        try {
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(getAccessToken());
            var httpEntity = new HttpEntity<String>(headers);
            var response = restTemplate.exchange(url + "/organisation/sensors", HttpMethod.GET, httpEntity, Sensor[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return List.of(Objects.requireNonNull(response.getBody()));
            }
        } catch (RuntimeException e) {
            var errorMessage = ErrorMessage.builder().error(Error.FARM21_COULD_NOT_FETCH_DEVICES).message("Could not fetch devices for Farm21 API.").build();
            log.error(errorMessage.asDetail());
        }
        log.warn("Could not fetch devices for Farm21 API, therefore returning empty list of devices.");
        return Collections.emptyList();
    }

}
