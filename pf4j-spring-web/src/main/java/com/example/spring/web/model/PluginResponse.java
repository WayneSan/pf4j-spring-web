package com.example.spring.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PluginResponse {
    private String id;
    private String description;
    @JsonProperty("class")
    private String pluginClass;
    private String version;
    private String requires;
    private String provider;
    private String license;
    private String state;
    private String runtimeMode;
}
