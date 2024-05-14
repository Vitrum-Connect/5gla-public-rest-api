package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agranimo.cache.UserDataCache;
import de.app.fivegla.integration.agranimo.model.Zone;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Service for integration with Agranimo.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgranimoZoneService {

    private final AgranimoLoginIntegrationService loginService;
    private final UserDataCache userDataCache;
    private final RestTemplate restTemplate;

    /**
     * Login against the API.
     */
    public List<Zone> fetchZones(ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        if (userDataCache.isExpired()) {
            try {
                var headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.setBearerAuth(loginService.fetchAccessToken(thirdPartyApiConfiguration));
                var httpEntity = new HttpEntity<>(headers);
                var uri = UriComponentsBuilder.fromHttpUrl(thirdPartyApiConfiguration.getUrl() + "/group")
                        .toUriString();
                var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Zone[].class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    log.error("Error while fetching zones from the API. Status code: {}", response.getStatusCode());
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.AGRANIMO_COULD_NOT_FETCH_ZONES)
                            .message("Could not fetch zones.")
                            .build());
                } else {
                    var zones = response.getBody();
                    if (null == zones) {
                        throw new BusinessException(ErrorMessage.builder()
                                .error(Error.AGRANIMO_COULD_NOT_FETCH_ZONES)
                                .message("Could not fetch zones. Response was empty.")
                                .build());
                    } else {
                        log.info("Successfully fetched zones from the API.");
                        userDataCache.setZones(List.of(zones));
                        return List.of(zones);
                    }
                }
            } catch (Exception e) {
                log.error("Error while fetching the zones.", e);
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.AGRANIMO_COULD_NOT_FETCH_ZONES)
                        .message("Could not fetch zones.")
                        .build());
            }
        } else {
            return userDataCache.getZones();
        }
    }

}
