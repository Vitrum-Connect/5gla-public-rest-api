package de.app.fivegla.integration.sensoterra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Response object.
 */
@Getter
@Setter
public class ApiKeyWithSettings {

    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("email")
    private String email;
    @JsonProperty("id")
    private int id;
    @JsonProperty("notifications_email")
    private String notificationsEmail;
    @JsonProperty("notifications_app")
    private String notificationsApp;
    @JsonProperty("role")
    private String role;
    @JsonProperty("readonly")
    private String readonly;
    @JsonProperty("disclaimer_agreed")
    private String disclaimerAgreed;

}
