package com.example.spring.web.service;

import java.nio.file.Path;
import java.util.List;

import com.example.spring.web.exception.PluginNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class PluginServiceImpl implements PluginService {

    private StorageService storageService;

    @Getter
    private PluginManager pluginManager;

    @Autowired
    public PluginServiceImpl(StorageService storageService,
                             PluginManager pluginManager) {

        this.storageService = storageService;
        this.pluginManager = pluginManager;
    }

    @Override
    public List<PluginWrapper> getAll() {
        return pluginManager.getPlugins();
    }

    @Override
    public List<PluginWrapper> getByState(PluginState pluginState) {
        return pluginManager.getPlugins(pluginState);
    }

    @Override
    public PluginWrapper get(String pluginId) {
        PluginWrapper plugin = pluginManager.getPlugin(pluginId);
        if (plugin == null) {
            log.warn("Plugin [" + pluginId + "] not found!!");
            throw new PluginNotFoundException("Plugin [" + pluginId + "] not found!!");
        }
        return plugin;
    }

    @Override
    public String installPlugin(MultipartFile file) {
        Path plugin = storageService.store(file);
        String pluginId = pluginManager.loadPlugin(plugin);
        pluginManager.startPlugin(pluginId);
        return pluginId;
    }

    @Override
    public boolean uninstallPlugin(String pluginId) {
        try {
            return pluginManager.deletePlugin(pluginId);
        } catch (IllegalArgumentException e) {
            throw new PluginNotFoundException("Plugin [" + pluginId + "] not found!!", e);
        }
    }
}
