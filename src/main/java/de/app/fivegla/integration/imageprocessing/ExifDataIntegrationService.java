package de.app.fivegla.integration.imageprocessing;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service for reading exif data from images.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExifDataIntegrationService {

    /**
     * Reads GPS data from image.
     *
     * @param image The image to read exif data from.
     * @return exif data from image.
     */
    public Point readLocation(byte[] image) {
        try {
            var metadata = (TiffImageMetadata) Imaging.getMetadata(image);
            var gps = metadata.getGPS();
            return new GeometryFactory().createPoint(new Coordinate(gps.getLongitudeAsDegreesEast(), gps.getLatitudeAsDegreesNorth()));
        } catch (Exception e) {
            log.error("Could not read image metadata", e);
            var errorMessage = ErrorMessage.builder().error(Error.MICASENSE_COULD_NOT_READ_IMAGE_METADATA).message("Could not read image metadata, please see log for more details.").build();
            throw new BusinessException(errorMessage);
        }
    }

    /**
     * Reads time data from image.
     *
     * @param image The image to read exif data from.
     * @return exif data from image.
     */
    public Instant readMeasuredAt(byte[] image) {
        try {
            var tiffFieldRef = new AtomicReference<TiffField>();
            ((TiffImageMetadata) Imaging.getMetadata(image))
                    .findDirectory(TiffDirectoryType.TIFF_DIRECTORY_ROOT.directoryType)
                    .entries.stream().filter(t -> t.getTag() == TiffTagConstants.TIFF_TAG_DATE_TIME.tag).findFirst().ifPresentOrElse(t -> {
                        log.debug("Found the following date time: {}", t);
                        tiffFieldRef.set(t);
                    }, () -> log.warn("Could not find date time in image metadata"));
            var tiffField = tiffFieldRef.get();
            if (null == tiffField) {
                log.error("Could not find date time in image metadata");
                var errorMessage = ErrorMessage.builder().error(Error.MICASENSE_COULD_NOT_READ_IMAGE_METADATA).message("Could not read image metadata, please see log for more details.").build();
                throw new BusinessException(errorMessage);
            } else {
                var dateTime = tiffField.getValueDescription().replace("'", "");
                log.debug("Parsing date time: {}", dateTime);
                return LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss")).atStartOfDay(ZoneId.systemDefault()).toInstant();
            }
        } catch (Exception e) {
            log.error("Could not read image metadata", e);
            var errorMessage = ErrorMessage.builder().error(Error.MICASENSE_COULD_NOT_READ_IMAGE_METADATA).message("Could not read image metadata, please see log for more details.").build();
            throw new BusinessException(errorMessage);
        }
    }
}
