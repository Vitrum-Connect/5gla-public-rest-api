package de.app.fivegla.api;

import lombok.Getter;

/**
 * Error codes.
 */
@Getter
public enum Error {

    INVALID_REQUEST(errorOf(1)),
    SENSOR_ALREADY_EXISTS(errorOf(2)),
    SENSOR_NOT_FOUND(errorOf(3)),
    SENSOR_TYPE_UNKNOWN(errorOf(4)),
    SOIL_SCOUT_COULD_NOT_AUTHENTICATE(errorOf(5));

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
