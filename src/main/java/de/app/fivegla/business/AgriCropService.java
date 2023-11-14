package de.app.fivegla.business;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.geojson.GeoJSONUtil;
import org.geotools.geojson.feature.FeatureHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AgriCropService {

    /**
     * Parses the GeoJSON file containing the agri-crop data.
     */
    public SimpleFeature parseFeature(String geoJson) {
        try {
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

}
