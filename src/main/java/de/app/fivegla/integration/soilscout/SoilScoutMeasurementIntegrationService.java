package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.soilscout.dto.response.SoilScoutMeasurementResponse;
import de.app.fivegla.integration.soilscout.model.SoilScoutSensorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Soil Scout integration service to access devices and sensor data.
 */
@Slf4j
@Service
public class SoilScoutMeasurementIntegrationService extends AbstractSoilScoutIntegrationService {

    /**
     * Fetch all soil scout sensor data.
     *
     * @return all soil scout data for the sensor
     */
    public List<SoilScoutSensorData> findAll(Instant since, Instant until) {
        return findAll(since, until, getAccessToken());
    }


    private List<SoilScoutSensorData> findAll(Instant since, Instant until, String accessToken) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken);
        var httpEntity = new HttpEntity<String>(headers);
        var uri = UriComponentsBuilder.fromHttpUrl(url + "/measurements/?since={since}&until={until}")
                .encode()
                .toUriString();
        var uriVariables = Map.of("since",
                formatInstant(since),
                "until",
                formatInstant(until));
        var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, SoilScoutMeasurementResponse.class, uriVariables);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.debug("There are {} sensor data sets in total.", Objects.requireNonNull(response.getBody()).getResults().length);
            return Arrays.asList(Objects.requireNonNull(response.getBody()).getResults());
        } else {
            var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch devices for SoilScout API.").build();
            throw new BusinessException(errorMessage);
        }
    }

    private String formatInstant(Instant instant) {
        if (instant == null) {
            return null;
        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date.from(instant));
        }
    }
}
