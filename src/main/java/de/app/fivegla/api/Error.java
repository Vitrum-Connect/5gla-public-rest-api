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
    AGVOLUTION_COULD_NOT_FETCH_TIME_SERIES(errorOf(11));


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
