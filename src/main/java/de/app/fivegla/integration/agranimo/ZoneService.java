package de.app.fivegla.integration.agranimo;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agranimo.cache.UserDataCache;
import de.app.fivegla.integration.agranimo.dto.Zone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class ZoneService {

    @Value("${app.sensors.agranimo.url}")
    private String url;

    private final LoginIntegrationService loginService;
    private final UserDataCache userDataCache;

    public ZoneService(LoginIntegrationService loginService, UserDataCache userDataCache) {
        this.loginService = loginService;
        this.userDataCache = userDataCache;
    }

    /**
     * Login against the API.
     */
    public List<Zone> fetchZones() {
        if (userDataCache.isExpired()) {
            try {
                var headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.setBearerAuth(loginService.fetchAccessToken());
                var httpEntity = new HttpEntity<>(headers);
                var uri = UriComponentsBuilder.fromHttpUrl(url + "/zone")
                        .toUriString();
                var response = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, Zone[].class);

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
