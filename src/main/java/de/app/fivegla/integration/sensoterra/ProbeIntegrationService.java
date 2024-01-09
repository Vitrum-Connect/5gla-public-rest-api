package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.sensoterra.model.Location;
import de.app.fivegla.integration.sensoterra.model.Probe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Integration service for the Sensoterra API.
 */
@Slf4j
@Service
public class ProbeIntegrationService extends AbstractIntegrationService {

    private final RestTemplate restTemplate;

    public ProbeIntegrationService(ApiKeyIntegrationService apiKeyIntegrationService,
                                   RestTemplate restTemplate) {
        super(apiKeyIntegrationService);
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches all probes from the Sensoterra API.
     *
     * @param location The location to fetch the probes for.
     * @return List of probes.
     */
    public List<Probe> fetchAll(Location location) {
        try {
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("api_key", getApiKey());
            var httpEntity = new HttpEntity<>(headers);
            var uri = UriComponentsBuilder.fromHttpUrl(url + "/probe/{locationId}")
                    .encode()
                    .toUriString();
            var uriVariables = Map.of(
                    "locationId",
                    location.getId());
            var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Probe[].class, uriVariables);
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error while fetching the probes from the API. Status code: {}", response.getStatusCode());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.SENSOTERRA_COULD_NOT_FETCH_PROBES)
                        .message("Could not fetch probes from the API.")
                        .build());
            } else {
                log.info("Successfully fetched probes from the API.");
                var probes = response.getBody();
                if (null == probes) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.SENSOTERRA_COULD_NOT_FETCH_PROBES)
                            .message("Could not fetch probes from the API. Response was empty.")
                            .build());
                } else {
                    return List.of(probes);
                }
            }
        } catch (Exception e) {
            log.error("Error while fetching the probes from API.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.SENSOTERRA_COULD_NOT_FETCH_PROBES)
                    .message("Could not fetch probes from the API.")
                    .build());
        }
    }

}
