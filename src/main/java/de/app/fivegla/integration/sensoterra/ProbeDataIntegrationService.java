package de.app.fivegla.integration.sensoterra;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Format;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.sensoterra.model.Probe;
import de.app.fivegla.integration.sensoterra.model.ProbeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Integration service for the Sensoterra API.
 */
@Slf4j
@Service
public class ProbeDataIntegrationService extends AbstractIntegrationService {

    private final LocationIntegrationService locationIntegrationService;
    private final ProbeIntegrationService probeIntegrationService;
    private final RestTemplate restTemplate;

    public ProbeDataIntegrationService(ApiKeyIntegrationService apiKeyIntegrationService,
                                       LocationIntegrationService locationIntegrationService,
                                       ProbeIntegrationService probeIntegrationService,
                                       RestTemplate restTemplate) {
        super(apiKeyIntegrationService);
        this.locationIntegrationService = locationIntegrationService;
        this.probeIntegrationService = probeIntegrationService;
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches all probes from the Sensoterra API.
     *
     * @param begin The beginning date to fetch the data for.
     * @return Map of probes.
     */
    public Map<Probe, List<ProbeData>> fetchAll(Instant begin) {
        var probeData = new HashMap<Probe, List<ProbeData>>();
        var locations = locationIntegrationService.fetchAll();
        locations.forEach(location -> probeIntegrationService.fetchAll(location).forEach(probe -> {
            try {
                var headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("api_key", getApiKey());
                var httpEntity = new HttpEntity<>(headers);
                var uri = UriComponentsBuilder.fromHttpUrl(url + "/probe/{probeId}/{from}/{to}")
                        .encode()
                        .toUriString();
                var uriVariables = Map.of(
                        "probeId",
                        probe.getId(),
                        "from",
                        Format.format(begin),
                        "to",
                        Format.format(Instant.now()));
                var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ProbeData[].class, uriVariables);
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
                        probeData.put(probe, List.of(probes));
                    }
                }
            } catch (Exception e) {
                log.error("Error while fetching the probes from API.", e);
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.SENSOTERRA_COULD_NOT_FETCH_PROBES)
                        .message("Could not fetch probes from the API.")
                        .build());
            }
        }));
        return probeData;
    }
}
