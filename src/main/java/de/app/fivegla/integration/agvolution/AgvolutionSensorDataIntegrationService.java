package de.app.fivegla.integration.agvolution;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.InstantFormat;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.agvolution.model.Device;
import de.app.fivegla.integration.agvolution.model.SeriesEntry;
import de.app.fivegla.integration.agvolution.request.QueryRequest;
import de.app.fivegla.integration.agvolution.response.DeviceTimeseriesDataResponse;
import de.app.fivegla.integration.agvolution.response.inner.DeviceTimeSeriesEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for the Farm21 API.
 */
@Slf4j
@Service
public class AgvolutionSensorDataIntegrationService extends AbstractIntegrationService {

    private static final String QUERY = """
            {
                deviceTimeseries(
                    device: "%s"
                    filter: { start: "%s" }
                ) {
                    series {
                        device
                        lon
                        lat
                        timeseries {
                            key
                            unit
                            aggregate
                            values {
                                time
                                value
                            }
                        }
                    }
                }
            }""";

    AgvolutionSensorDataIntegrationService(AccessTokenService accessTokenService) {
        super(accessTokenService);
    }

    /**
     * Fetches all sensors from the SoilScout API.
     *
     * @return List of sensors.
     */
    public List<SeriesEntry> findAll(Device device, Instant begin) {
        try {
            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAccessToken());
            var httpEntity = new HttpEntity<>(new QueryRequest(String.format(QUERY, device.getId(), InstantFormat.format(begin))), headers);
            var response = restTemplate.postForEntity(url + "/devices", httpEntity, DeviceTimeseriesDataResponse.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error while login against the API. Status code: {}", response.getStatusCode());
                throw new BusinessException(ErrorMessage.builder()
                        .error(Error.AGVOLUTION_COULD_NOT_FETCH_TIME_SERIES)
                        .message("Could not fetch time series from the API.")
                        .build());
            } else {
                log.info("Successfully fetched time series from the API.");
                var devices = response.getBody();
                if (null == devices) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.AGVOLUTION_COULD_NOT_FETCH_TIME_SERIES)
                            .message("Could not fetch time series from the API. Response was empty.")
                            .build());
                } else {
                    return devices.getData().getDeviceTimeSeriesEntries().stream()
                            .map(DeviceTimeSeriesEntry::getSeriesEntries)
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
                }
            }
        } catch (Exception e) {
            log.error("Error while fetching the devices from API.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.AGVOLUTION_COULD_NOT_FETCH_TIME_SERIES)
                    .message("Could not fetch time series from the API.")
                    .build());
        }
    }

}
