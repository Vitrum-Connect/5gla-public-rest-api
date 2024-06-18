package de.app.fivegla.controller.api;

/**
 * A class that contains the base mappings for the API.
 */
public class BaseMappings {

    // GLOBAL

    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String SWAGGER_V3 = "/v3/api-docs";
    public static final String ACTUATOR = "/actuator";
    public static final String ERROR = "/error";

    // API

    public static final String API_V1 = "/v1";
    public static final String SECURED_BY_API_KEY = API_V1 + "/sec/api-key";
    public static final String SECURED_BY_TENANT = API_V1 + "/sec/tenant";

    // SECURED_BY_API_KEY

    public static final String MAINTENANCE = SECURED_BY_API_KEY + "/maintenance";
    public static final String INFO = SECURED_BY_API_KEY + "/info";
    public static final String TENANT = SECURED_BY_API_KEY + "/tenant";

    // SECURED_BY_TENANT

    public static final String IMAGE_PROCESSING = SECURED_BY_TENANT + "/image-processing";
    public static final String AGRI_CROP = SECURED_BY_TENANT + "/agri-crop";
    public static final String THIRD_PARTY_API_CONFIGURATION = SECURED_BY_TENANT + "/3rd-party-api-configuration";
    public static final String DEVICE_POSITION = SECURED_BY_TENANT + "/device-position";
    public static final String GROUPS = SECURED_BY_TENANT + "/groups";
    public static final String SUBSCRIPTION = SECURED_BY_TENANT + "/subscription";
}
