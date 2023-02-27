package de.app.fivegla.integration.soilscout;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.soilscout.dto.request.SoilScoutSsoRequest;
import de.app.fivegla.integration.soilscout.dto.request.SoilScoutTokenRequest;
import de.app.fivegla.integration.soilscout.dto.response.SoilScoutAccessTokenResponse;
import de.app.fivegla.integration.soilscout.dto.response.SoilScoutMeasurementResponse;
import de.app.fivegla.integration.soilscout.dto.response.SoilScoutSsoResponse;
import de.app.fivegla.integration.soilscout.model.SoilScoutData;
import de.app.fivegla.integration.soilscout.model.SoilScoutSensor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class SoilScoutIntegrationService {

    @Value("${app.sensors.soilscout.url}")
    private String url;
    @Value("${app.sensors.soilscout.username}")
    private String username;
    @Value("${app.sensors.soilscout.password}")
    private String password;

    /**
     * Fetch all soil scout sensor data.
     *
     * @return all soil scout data for the sensor
     */
    public List<SoilScoutData> findAllMeasurements(Instant since, Instant until) {
        var soilScoutSsoResponse = getSSOToken();
        var soilScoutAccessTokenResponse = getBearerToken(soilScoutSsoResponse.getSsoToken());
        return findAllMeasurements(since, until, soilScoutAccessTokenResponse.getAccess());
    }

    private List<SoilScoutData> findAllMeasurements(Instant since, Instant until, String accessToken) {
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
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date.from(instant));
    }

    /**
     * Fetches all sensors from the SoilScout API.
     *
     * @return List of sensors.
     */
    public List<SoilScoutSensor> findAllSensors() {
        var soilScoutSsoResponse = getSSOToken();
        var soilScoutAccessTokenResponse = getBearerToken(soilScoutSsoResponse.getSsoToken());
        return getDevices(soilScoutAccessTokenResponse.getAccess());
    }

    private List<SoilScoutSensor> getDevices(String accessToken) {
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

    private SoilScoutAccessTokenResponse getBearerToken(String ssoToken) {
        var soilScoutTokenRequest = SoilScoutTokenRequest.builder()
                .ssoToken(ssoToken)
                .build();
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        var httpEntity = new HttpEntity<>(soilScoutTokenRequest, headers);
        var response = restTemplate.exchange(url + "/auth/token/sso/", HttpMethod.POST, httpEntity, SoilScoutAccessTokenResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch bearer token for SoilScout API.").build();
            throw new BusinessException(errorMessage);
        }
    }

    private SoilScoutSsoResponse getSSOToken() {
        var soilScoutSsoRequest = SoilScoutSsoRequest.builder()
                .username(username)
                .password(password)
                .build();
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        var httpEntity = new HttpEntity<>(soilScoutSsoRequest, headers);
        var response = restTemplate.exchange(url + "/auth/login/sso/", HttpMethod.POST, httpEntity, SoilScoutSsoResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            var errorMessage = ErrorMessage.builder().error(Error.SOIL_SCOUT_COULD_NOT_AUTHENTICATE).message("Could not fetch SSO token for SoilScout API.").build();
            throw new BusinessException(errorMessage);
        }
    }
}
