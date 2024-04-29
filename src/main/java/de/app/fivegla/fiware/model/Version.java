package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a version.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Version {

    /**
     * The version of the orion ld.
     */
    private String orionLdVersion;

    /**
     * The version of the orion.
     */
    private String orionVersion;

    /**
     * The uptime.
     */
    private String uptime;

    /**
     * The git hash.
     */
    @JsonProperty("git_hash")
    private String gitHash;

    /**
     * The compile time.
     */
    @JsonProperty("compile_time")
    private String compileTime;

    /**
     * The compiled by.
     */
    @JsonProperty("compiled_by")
    private String compiledBy;

    /**
     * The compiled in.
     */
    @JsonProperty("compiled_in")
    private String compiledIn;

    /**
     * The release date.
     */
    @JsonProperty("release_date")
    private String releaseDate;

    /**
     * The doc.
     */
    private String doc;
}
