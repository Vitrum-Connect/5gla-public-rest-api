package de.app.fivegla.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for information purpose.
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${app.version:unknown}")
    private String applicationVersion;

    /**
     * Returns the version of the application.
     *
     * @return the version of the application
     */
    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getVersion() {
        return applicationVersion;
    }


}
