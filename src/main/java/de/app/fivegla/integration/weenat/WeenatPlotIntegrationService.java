package de.app.fivegla.integration.weenat;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.weenat.model.Plot;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class WeenatPlotIntegrationService {

    private final WeenatAccessTokenIntegrationService weenatAccessTokenIntegrationService;
    private final RestTemplate restTemplate;

    /**
     * Fetches all metadata from the API.
     *
     * @return a list of metadata objects
     * @throws BusinessException if there was an error fetching the metadata
     */
    public List<Plot> fetchAll(ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(weenatAccessTokenIntegrationService.fetchAccessToken(thirdPartyApiConfiguration));
        var httpEntity = new HttpEntity<String>(headers);
        var response = restTemplate.exchange(thirdPartyApiConfiguration.getUrl() + "/v2/access/plots", HttpMethod.GET, httpEntity, Plot[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Could not fetch metadata from the API. Response code was {}.", response.getStatusCode());
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.WEENAT_COULD_NOT_FETCH_PLOTS)
                    .message("Could not fetch metadata from the API.")
                    .build());
        } else {
            log.info("Successfully fetched metadata from the API.");
            var metadataResponse = response.getBody();
            if (null == metadataResponse) {
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.WEENAT_COULD_NOT_FETCH_PLOTS)
                        .message("Could not fetch metadata from the API. Response was empty.")
                        .build());
            } else {
                log.info("Successfully fetched metadata from the API.");
                return List.of(metadataResponse);
            }
        }
    }

}
