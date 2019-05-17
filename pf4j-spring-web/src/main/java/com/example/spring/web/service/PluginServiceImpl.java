package com.example.spring.web.service;

import java.nio.file.Path;
import java.util.List;

import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PluginServiceImpl implements PluginService {

    private StorageService storageService;
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
        return pluginManager.getPlugin(pluginId);
    }

    @Override
    public void installPlugin(MultipartFile file) {
        Path plugin = storageService.store(file);
        pluginManager.loadPlugin(plugin);
    }
}
