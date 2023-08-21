package de.app.fivegla.integration.sentek;

import com.opencsv.bean.CsvToBeanBuilder;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.Format;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.integration.sentek.model.csv.Reading;
import de.app.fivegla.integration.sentek.model.xml.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.StringReader;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date Time,V1,V2,A1(5),T1(5),A2(15),T2(15),A3(25),T3(25),A4(35),T4(35),A5(45),T5(45),A6(55),T6(55),A7(65),T7(65),A8(75),T8(75),A9(85),T9(85)
 */
@Slf4j
@Service
public class SentekSensorDataIntegrationService extends AbstractIntegrationService {

    private final SentekSensorIntegrationService sentekSensorIntegrationService;

    public SentekSensorDataIntegrationService(SentekSensorIntegrationService sentekSensorIntegrationService) {
        this.sentekSensorIntegrationService = sentekSensorIntegrationService;
    }

    /**
     * Fetches all readings for a given timestamp from all sensors.
     *
     * @param from the starting timestamp to fetch readings from
     * @return a map containing the sensor names as keys and a list of readings as values
     */
    public Map<Logger, List<Reading>> fetchAll(Instant from) {
        var readings = new HashMap<Logger, List<Reading>>();
        var sensors = sentekSensorIntegrationService.fetchAll();
        sensors.forEach(sensor -> {
            var allReadingsForSensor = fetchAll(sensor.getName(), from);
            readings.put(sensor, allReadingsForSensor);
        });
        return readings;
    }

    /**
     * Fetches all readings from the Sentek API for a given logger name and starting time.
     *
     * @param loggerName The name of the logger to fetch readings from.
     * @param from       The starting time to fetch readings from.
     * @return A list of Reading objects representing the sensor data.
     * @throws BusinessException If an error occurs while fetching the sensor data.
     */
    protected List<Reading> fetchAll(String loggerName, Instant from) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        var httpEntity = new HttpEntity<String>(headers);
        var uri = UriComponentsBuilder.fromHttpUrl(url + "/?cmd=getreadings&key={apiToken}&name={loggerName}&from={from}")
                .encode()
                .toUriString();
        log.debug("Fetching sensor data from URI: {}", uri);
        var uriVariables = Map.of("apiToken", getApiToken(),
                "loggerName", loggerName,
                "from", Format.formatForIrrimax(from));
        var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class, uriVariables);
        if (response.getStatusCode().is2xxSuccessful()) {
            return parse(response.getBody());
        } else {
            var errorMessage = ErrorMessage.builder()
                    .error(Error.SENTEK_COULD_NOT_FETCH_SENSOR_DATA)
                    .message("Could not fetch sensor data from Sentek API.")
                    .build();
            throw new BusinessException(errorMessage);
        }
    }

    /**
     * Parses the CSV data into a list of Reading objects.
     *
     * @param csv The CSV data to be parsed.
     * @return A list of Reading objects representing the sensor data.
     */
    protected List<Reading> parse(String csv) {
        var csvReader = new CsvToBeanBuilder<Reading>(new StringReader(csv))
                .withType(Reading.class)
                .build();
        return csvReader.parse();
    }

}
