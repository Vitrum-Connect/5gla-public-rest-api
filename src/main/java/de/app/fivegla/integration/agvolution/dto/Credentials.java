package de.app.fivegla.integration.agvolution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * User credentials.
 */
@Getter
@Setter
public class Credentials {

    @JsonProperty("id_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

}
