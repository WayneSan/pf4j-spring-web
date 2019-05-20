package com.example.spring.web.controller;

import java.util.List;

import com.example.spring.web.exception.PluginNotFoundException;
import com.example.spring.web.exception.ServiceRuntimeException;
import com.example.spring.web.model.Plugin;
import com.example.spring.web.service.PluginService;
import ma.glasnost.orika.MapperFacade;
import org.pf4j.PluginState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "state/{pluginState}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Plugin> getByState(@PathVariable PluginState pluginState) {
        return mapperFacade.mapAsList(pluginService.getByState(pluginState), Plugin.class);
    }

    @GetMapping(value = "{pluginId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Plugin get(@PathVariable String pluginId) {
        return mapperFacade.map(pluginService.get(pluginId), Plugin.class);
    }

    @PutMapping(value = "{pluginId}/{operation}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Plugin operation(@PathVariable String pluginId, @PathVariable String operation) {
        try {
            switch (operation.toLowerCase()) {
                case "start":
                    pluginService.getPluginManager().startPlugin(pluginId);
                    break;
                case "stop":
                    pluginService.getPluginManager().stopPlugin(pluginId);
                    break;
                case "enable":
                    pluginService.getPluginManager().enablePlugin(pluginId);
                    break;
                case "disable":
                    pluginService.getPluginManager().disablePlugin(pluginId);
                    break;
                default:
                    throw new ServiceRuntimeException("Unknown operation [" + operation + "]!!");
            }
        } catch (IllegalArgumentException e) {
            throw new PluginNotFoundException("Plugin [" + pluginId + "] not found!!", e);
        }
        return mapperFacade.map(pluginService.get(pluginId), Plugin.class);
    }

    @DeleteMapping(value = "{pluginId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable String pluginId) {
        pluginService.uninstallPlugin(pluginId);
        return ResponseEntity.noContent().build();
    }
}
