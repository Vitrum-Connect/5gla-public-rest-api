package de.app.fivegla.api;

import org.apache.commons.lang3.StringUtils;

/**
 * Class representing a ZoneOrDefaultValue.
 * <p>
 * This class is a record that includes a zone value. If the zone is blank, it returns a default value.
 */
public record ZoneOrDefaultValue(String zone) {

    public static final String DEFAULT_VALUE = "default";
    public static final ZoneOrDefaultValue DEFAULT = new ZoneOrDefaultValue(DEFAULT_VALUE);

    /**
     * Retrieves the value of the zone.
     * <p>
     * If the zone is blank, the method returns the default value.
     *
     * @return the value of the zone or the default value
     */
    public String value() {
        return StringUtils.isBlank(zone) ? DEFAULT_VALUE : zone;
    }

}
