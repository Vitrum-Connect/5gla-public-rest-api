package de.app.fivegla.integration.weenat;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.integration.weenat.model.Plot;
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
public class WeenatPlotIntegrationService extends AbstractIntegrationService {

    private final WeenatAccessTokenIntegrationService weenatAccessTokenIntegrationService;
    private final RestTemplate restTemplate;

    /**
     * Fetches all metadata from the API.
     *
     * @return a list of metadata objects
     * @throws BusinessException if there was an error fetching the metadata
     */
    public List<Plot> fetchAll() {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(weenatAccessTokenIntegrationService.fetchAccessToken());
        var httpEntity = new HttpEntity<String>(headers);
        var response = restTemplate.exchange(getUrl() + "/v2/access/plots", HttpMethod.GET, httpEntity, Plot[].class);
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

    /**
     * Retrieves information from a device and creates a `Plot` object.
     *
     * @param device the device from which to extract the information
     * @return the `Plot` object created from the device information
     */
    public Plot plotFromDevice(Device device) {
        var plot = new Plot();
        plot.setId(Long.valueOf(device.getManufacturerSpecificId()));
        plot.setName("Plot " + device.getManufacturerSpecificId());
        plot.setLatitude(device.getLocation().getCoordinates().get(0));
        plot.setLongitude(device.getLocation().getCoordinates().get(1));
        plot.setDeviceCount(1);
        return plot;

    }
}
