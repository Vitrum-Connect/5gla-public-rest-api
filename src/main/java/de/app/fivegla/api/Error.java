package de.app.fivegla.api;

import lombok.Getter;

/**
 * Error codes.
 */
@Getter
public enum Error {

    INVALID_REQUEST(errorOf(1)),
    SOIL_SCOUT_COULD_NOT_AUTHENTICATE(errorOf(2)),
    SOIL_SCOUT_CSV_DATA_IMPORT_FAILED(errorOf(3)),
    FARM21_COULD_NOT_AUTHENTICATE(errorOf(4)),
    AGRANIMO_COULD_NOT_LOGIN_AGAINST_API(errorOf(5)),
    AGRANIMO_COULD_NOT_FETCH_ZONES(errorOf(6)),
    AGRANIMO_COULD_NOT_FETCH_SOIL_MOISTURE_FOR_ZONE(errorOf(7)),
    MICASENSE_COULD_NOT_READ_IMAGE_METADATA(errorOf(8)),
    AGVOLUTION_COULD_NOT_LOGIN_AGAINST_API(errorOf(9)),
    AGVOLUTION_COULD_NOT_FETCH_DEVICES(errorOf(10)),
    AGVOLUTION_COULD_NOT_FETCH_TIME_SERIES(errorOf(11)),
    SENSOTERRA_COULD_NOT_LOGIN_AGAINST_API(errorOf(12)),
    SENSOTERRA_COULD_NOT_FETCH_LOCATIONS(errorOf(13)),
    SENSOTERRA_COULD_NOT_FETCH_PROBES(errorOf(14)),
    SENTEK_XML_PARSING_ERROR(errorOf(15)),
    SENTEK_COULD_NOT_FETCH_SENSORS(errorOf(16)),
    FARM21_COULD_NOT_FETCH_DEVICES(errorOf(17)),
    SENTEK_COULD_NOT_FETCH_SENSOR_DATA(errorOf(18)),
    WEENAT_COULD_NOT_LOGIN_AGAINST_API(errorOf(19)),
    WEENAT_COULD_NOT_FETCH_PLOTS(errorOf(20)),
    WEENAT_COULD_NOT_FETCH_MEASURES(errorOf(21)),
    SENTEK_COULD_NOT_FIND_SENSOR_FOR_ID(errorOf(22)),
    WEENAT_COULD_NOT_FIND_SENSOR_FOR_ID(errorOf(23)),
    AGVOLUTION_COULD_NOT_FIND_SENSOR_FOR_ID(errorOf(24)),
    FIWARE_INTEGRATION_LAYER_ERROR(errorOf(25)),
    FARM21_COULD_NOT_FETCH_DEVICE_DATA(errorOf(26)),
    COULD_NOT_PARSE_GEO_JSON(errorOf(27)),
    COULD_NOT_PARSE_CSV(errorOf(28)),
    THIRD_PARTY_SERVICE_UNAVAILABLE(errorOf(29));

    private static String errorOf(int i) {
        return ERR_ + String.format("%05d", i);
    }

    private static final String ERR_ = "ERR_";

    private final String code;

    Error(String code) {
        this.code = code;
    }

    public String asTitle() {
        return String.format("%s", code);
    }
}
