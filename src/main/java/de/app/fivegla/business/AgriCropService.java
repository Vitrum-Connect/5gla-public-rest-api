package de.app.fivegla.business;

import com.opencsv.CSVParser;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.agricrop.GpsCoordinate;
import de.app.fivegla.integration.agricrop.AgriCropFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.fiware.model.AgriCrop;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgriCropService {

    private final AgriCropFiwareIntegrationServiceWrapper agriCropFiwareIntegrationServiceWrapper;

    /**
     * Creates a feature from a CSV file. Each line of the CSV file should contain the
     * coordinates of a point in the format "latitude, longitude". The feature will have a
     * unique ID generated using UUID.
     *
     * @param tenant The tenant owning the feature.
     * @param group   The group of the feature
     * @param cropId The ID of the featurel
     * @param csv    The CSV file containing the coordinates.
     * @return The created feature.
     * @throws BusinessException If the CSV file cannot be parsed, or if a line does not
     *                           contain exactly two columns.
     */
    public AgriCrop createFromCsv(Tenant tenant, Group group, String cropId, String csv) {
        if (StringUtils.isNotBlank(csv)) {
            log.info("Tenant {} is parsing CSV.", tenant.getName());
            log.debug("Parsing CSV: {}.", csv);
            var lines = csv.split("\n");
            log.debug("Looks like we have {} lines.", lines.length);
            var coordinates = parseCoordinates(lines);
            return agriCropFiwareIntegrationServiceWrapper.persist(tenant, group, cropId, coordinates);
        } else {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.COULD_NOT_PARSE_CSV)
                    .message("Failed to parse CSV since it was empty.")
                    .build());
        }
    }

    private List<GpsCoordinate> parseCoordinates(String[] lines) {
        try {
            var gpsCoordinates = new ArrayList<GpsCoordinate>();
            for (String line : lines) {
                var csvDataParsed = new CSVParser().parseLine(line);
                if (csvDataParsed.length != 2) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.COULD_NOT_PARSE_CSV)
                            .message("Failed to parse CSV since this was not a point, there are more than two coordinates.")
                            .build());
                } else {
                    var gpsCoordinate = GpsCoordinate.builder()
                            .latitude(Double.parseDouble(csvDataParsed[0]))
                            .longitude(Double.parseDouble(csvDataParsed[1])).build();
                    log.debug("Parsed GPS coordinate: {}.", gpsCoordinate);
                    gpsCoordinates.add(gpsCoordinate);
                }
            }
            return gpsCoordinates;
        } catch (Exception e) {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.COULD_NOT_CREATE_GPS_COORDINATES)
                    .message("Failed to create GPS coordinates.")
                    .build());
        }
    }
}
