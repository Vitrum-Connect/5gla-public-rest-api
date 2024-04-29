package de.app.fivegla.business;

import com.opencsv.CSVParser;
import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.business.constants.JSONObjectTypes;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.geojson.GeoJSONUtil;
import org.geotools.geojson.feature.FeatureHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgriCropService {

    /**
     * Parses the GeoJSON file containing the agri-crop data.
     */
    public SimpleFeature createFeatureFromGeoJson(Tenant tenant, String geoJson) {
        try {
            log.info("Tenant {} is parsing GeoJSON.", tenant.getName());
            log.debug("Parsing GeoJSON: {}.", geoJson);
            var featureHandler = new FeatureHandler();
            return GeoJSONUtil.parse(featureHandler, geoJson, true);
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
    @SuppressWarnings("unchecked")
    public SimpleFeature createFeatureFromCsv(Tenant tenant, String csv) {
        if (StringUtils.isNotBlank(csv)) {
            log.info("Tenant {} is parsing CSV.", tenant.getName());
            log.debug("Parsing CSV: {}.", csv);
            var lines = csv.split("\n");
            log.debug("Looks like we have {} lines.", lines.length);

            var featureCollection = new JSONObject();
            featureCollection.put("type", JSONObjectTypes.FEATURE_COLLECTION);
            featureCollection.put("features", createFeatures(lines));

            String jsonString = featureCollection.toJSONString();
            return createFeatureFromGeoJson(tenant, jsonString);
        } else {
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.COULD_NOT_PARSE_CSV)
                    .message("Failed to parse CSV since it was empty.")
                    .build());
        }
    }

    @SuppressWarnings("unchecked")
    private JSONArray createFeatures(String[] lines) {
        var features = new JSONArray();
        var feature = new JSONObject();
        feature.put("type", JSONObjectTypes.FEATURE);
        feature.put("properties", new JSONObject());
        feature.put("geometry", createGeometry(lines));
        features.add(feature);
        return features;
    }

    @SuppressWarnings("unchecked")
    private JSONObject createGeometry(String[] lines) {
        try {
            var geometry = new JSONObject();
            for (String line : lines) {
                var csvDataParsed = new CSVParser().parseLine(line);
                if (csvDataParsed.length != 2) {
                    throw new BusinessException(ErrorMessage.builder()
                            .error(Error.COULD_NOT_PARSE_CSV)
                            .message("Failed to parse CSV since this was not a point, there are more than two coordinates.")
                            .build());
                }
                var coordinates = new JSONArray();
                coordinates.add(0, Double.parseDouble(csvDataParsed[0]));
                coordinates.add(1, Double.parseDouble(csvDataParsed[1]));
                geometry.put("type", JSONObjectTypes.POLYGON);
                geometry.put("coordinates", coordinates);
            }
            return geometry;
        } catch (Exception e) {
            log.error("Failed to parse CSV.", e);
            throw new BusinessException(ErrorMessage.builder()
                    .error(Error.COULD_NOT_PARSE_CSV)
                    .message("Failed to parse CSV.")
                    .build());
        }
    }
}
