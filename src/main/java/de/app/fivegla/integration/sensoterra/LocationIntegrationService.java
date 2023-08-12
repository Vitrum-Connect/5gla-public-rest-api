package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.sensoterra.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Service for the Sensoterra API.
 */
@Slf4j
@Service
public class LocationIntegrationService extends AbstractIntegrationService {

    public LocationIntegrationService(ApiKeyIntegrationService apiKeyIntegrationService) {
        super(apiKeyIntegrationService);
    }

    /**
     * Fetches all locations from the API.
     *
     * @return List of locations.
     */
    public List<Location> fetchAll() {
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("api_key", getApiKey());
            var httpEntity = new HttpEntity<>(headers);
            var response = restTemplate.exchange(url + "/location", HttpMethod.GET, httpEntity, Location[].class);
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error while fetching the locations from the API. Status code: {}", response.getStatusCode());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.SENSOTERRA_COULD_NOT_FETCH_LOCATIONS)
                        .message("Could not fetch locations from the API.")
                        .build());
            } else {
                log.info("Successfully fetched locations from the API.");
                var locations = response.getBody();
                if (null == locations) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.SENSOTERRA_COULD_NOT_FETCH_LOCATIONS)
                            .message("Could not fetch locations from the API. Response was empty.")
                            .build());
                } else {
                    return List.of(locations);
                }
            }
        } catch (Exception e) {
            log.error("Error while fetching the locations from API.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.SENSOTERRA_COULD_NOT_FETCH_LOCATIONS)
                    .message("Could not fetch locations from the API.")
                    .build());
        }
    }

}
