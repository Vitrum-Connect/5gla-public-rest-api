package de.app.fivegla.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SmartModelEntityType {
    DEVICE_MEASUREMENT("DeviceMeasurement");

    private final String key;
}
