package com.example.spring.web.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("plugin")
public class PluginProperties {

    /**
     * Folder location for plugin files
     */
    private String location = "plugins";

}
