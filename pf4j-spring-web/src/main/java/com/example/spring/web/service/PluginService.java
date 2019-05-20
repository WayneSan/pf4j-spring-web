package com.example.spring.web.service;

import java.util.List;

import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.web.multipart.MultipartFile;

public interface PluginService {

    PluginManager getPluginManager();

    List<PluginWrapper> getAll();

    List<PluginWrapper> getByState(PluginState pluginState);

    PluginWrapper get(String pluginId);

    String installPlugin(MultipartFile file);

    boolean uninstallPlugin(String pluginId);

}
