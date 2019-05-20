package com.example.spring.web.service;

import java.util.List;

import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.web.multipart.MultipartFile;

public interface PluginService {

    List<PluginWrapper> getAll();

    List<PluginWrapper> getByState(PluginState pluginState);

    PluginWrapper get(String pluginId);

    String installPlugin(MultipartFile file);
}
