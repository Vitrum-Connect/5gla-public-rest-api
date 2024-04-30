package de.app.fivegla.business;

import com.opencsv.CSVParser;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.agricrop.GpsCoordinate;
import de.app.fivegla.integration.agricrop.AgriCropFiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.fiware.model.AgriCrop;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.geotools.geojson.GeoJSONUtil;
import org.geotools.geojson.feature.FeatureHandler;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgriCropService {

    private final AgriCropFiwareIntegrationServiceWrapper agriCropFiwareIntegrationServiceWrapper;

    /**
     * Parses the GeoJSON file containing the agri-crop data.
     */
    public AgriCrop createFromGeoJson(Tenant tenant, String cropId, String geoJson) {
        try {
            log.info("Tenant {} is parsing GeoJSON.", tenant.getName());
            log.debug("Parsing GeoJSON: {}.", geoJson);
            var featureHandler = new FeatureHandler();
            var feature = GeoJSONUtil.parse(featureHandler, geoJson, true);
            var defaultGeometry = (Geometry) feature.getDefaultGeometry();
            var coordinates = Arrays.stream(defaultGeometry.getCoordinates())
                    .map(coordinate -> GpsCoordinate.builder().latitude(coordinate.y).longitude(coordinate.x).build())
                    .toList();
            return agriCropFiwareIntegrationServiceWrapper.persist(tenant, cropId, coordinates);
        } catch (Exception e) {
            log.error("Failed to parse GeoJSON: {}.", geoJson, e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.COULD_NOT_PARSE_GEO_JSON)
                    .message("Failed to parse GeoJSON.")
                    .build());
        }
    }

    /**
     * Creates a feature from a CSV file. Each line of the CSV file should contain the
     * coordinates of a point in the format "latitude, longitude". The feature will have a
     * unique ID generated using UUID.
     *
     * @param csv The CSV file containing the coordinates.
     * @return The created feature.
     * @throws BusinessException If the CSV file cannot be parsed, or if a line does not
     *                           contain exactly two columns.
     */
    public AgriCrop createFromCsv(Tenant tenant, String cropId, String csv) {
        if (StringUtils.isNotBlank(csv)) {
            log.info("Tenant {} is parsing CSV.", tenant.getName());
            log.debug("Parsing CSV: {}.", csv);
            var lines = csv.split("\n");
            log.debug("Looks like we have {} lines.", lines.length);
            var coordinates = parseCoordinates(lines);
            agriCropFiwareIntegrationServiceWrapper.persist(tenant, cropId, coordinates);
            return agriCropFiwareIntegrationServiceWrapper.persist(tenant, cropId, coordinates);
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
