package com.example.spring.web;

import com.example.spring.web.properties.PluginProperties;
import com.example.spring.web.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ StorageProperties.class, PluginProperties.class })
public class Pf4jSpringWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pf4jSpringWebApplication.class, args);
    }

}
