package com.example.spring.web.controller;

import java.util.List;

import com.example.spring.web.exception.PluginNotFoundException;
import com.example.spring.web.model.Plugin;
import com.example.spring.web.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/plugins")
public class PluginController {

    private MapperFacade mapperFacade;
    private PluginService pluginService;

    @Autowired
    public PluginController(MapperFacade mapperFacade,
                            PluginService pluginService) {
        this.mapperFacade = mapperFacade;
        this.pluginService = pluginService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Plugin> getAll() {
        return mapperFacade.mapAsList(pluginService.getAll(), Plugin.class);
    }

    @GetMapping(value = "{pluginId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Plugin get(@PathVariable String pluginId) {
        return mapperFacade.map(pluginService.get(pluginId), Plugin.class);
    }

    @ExceptionHandler(PluginNotFoundException.class)
    public ResponseEntity<?> handlePluginNotFound(PluginNotFoundException ex) {
        if (log.isInfoEnabled()) {
            log.warn(ex.getMessage(), ex);
        }
        return ResponseEntity.notFound().build();
    }
}
