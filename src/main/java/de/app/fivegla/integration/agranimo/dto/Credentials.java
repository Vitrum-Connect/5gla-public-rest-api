package de.app.fivegla.integration.agranimo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * User credentials.
 */
@Getter
@Setter
public class Credentials {

    private String id;
    private String username;
    private String email;
    private String role;
    private String accessToken;

}
