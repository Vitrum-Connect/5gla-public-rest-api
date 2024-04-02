package de.app.fivegla.integration.agvolution;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agvolution.dto.request.QueryRequest;
import de.app.fivegla.integration.agvolution.dto.response.DeviceDataResponse;
import de.app.fivegla.integration.agvolution.model.Device;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Service for the Farm21 API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgvolutionSensorIntegrationService {

    private final RestTemplate restTemplate;
    private final AccessTokenIntegrationService accessTokenIntegrationService;

    /**
     * Fetches all sensors from the SoilScout API.
     *
     * @return List of sensors.
     */
    public List<Device> fetchAll(ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        try {
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessTokenIntegrationService.fetchAccessToken(thirdPartyApiConfiguration));
            var httpEntity = new HttpEntity<>(new QueryRequest("{devices{id,position{lon,lat},latestSignal}}"), headers);
            var response = restTemplate.postForEntity(thirdPartyApiConfiguration.getUrl() + "/devices", httpEntity, DeviceDataResponse.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error while fetching devices from the API. Status code: {}", response.getStatusCode());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.AGVOLUTION_COULD_NOT_FETCH_DEVICES)
                        .message("Could not fetch devices from the API.")
                        .build());
            } else {
                log.info("Successfully fetched devices from the API.");
                var devices = response.getBody();
                if (null == devices) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.AGVOLUTION_COULD_NOT_FETCH_DEVICES)
                            .message("Could not fetch devices from the API. Response was empty.")
                            .build());
                } else {
                    log.info("Devices found: {}", devices.getData().getDevices());
                    return devices.getData().getDevices();
                }
            }
        } catch (Exception e) {
            log.error("Error while fetching the devices from API.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.AGVOLUTION_COULD_NOT_FETCH_DEVICES)
                    .message("Could not fetch devices from the API.")
                    .build());
        }
    }

}
