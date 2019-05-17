package com.example.spring.web;

import java.nio.file.Paths;

import com.example.spring.web.model.Plugin;
import com.example.spring.web.properties.PluginProperties;
import com.example.spring.web.properties.StorageProperties;
import com.example.spring.web.service.StorageService;
import com.example.spring.web.service.StorageServiceImpl;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class Pf4jSpringConfiguration {

    @Bean
    StorageService storageService(StorageProperties properties) {
        StorageService storageService = new StorageServiceImpl(properties);
        storageService.deleteAll();
        storageService.init();
        return storageService;
    }

    @Bean
    @DependsOn("storageService")
    SpringPluginManager pluginManager(PluginProperties properties) {
        return new SpringPluginManager(Paths.get(properties.getLocation()));
    }

    @Bean
    OrikaMapperFactoryConfigurer orikaMapper() {
        return orikaMapperFactory -> {
            orikaMapperFactory.classMap(PluginWrapper.class, Plugin.class)
                    .fieldAToB("descriptor.pluginId", "id")
                    .fieldAToB("descriptor.pluginDescription", "description")
                    .fieldAToB("descriptor.pluginClass", "pluginClass")
                    .fieldAToB("descriptor.version", "version")
                    .fieldAToB("descriptor.requires", "requires")
                    .fieldAToB("descriptor.provider", "provider")
                    .fieldAToB("descriptor.license", "license")
                    .fieldAToB("pluginState", "state")
                    .fieldAToB("runtimeMode", "runtimeMode")
                    .register();
        };
    }

}
