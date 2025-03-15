package com.culture.association_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KrakenResponse {

    private boolean success;

    @JsonProperty("kraked_url")
    private String krakedUrl;

    private String message; // Error message (if any)

}