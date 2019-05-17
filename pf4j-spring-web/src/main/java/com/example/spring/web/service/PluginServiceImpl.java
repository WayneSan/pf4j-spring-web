package com.example.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PluginServiceImpl implements PluginService {

    private StorageService storageService;

    @Autowired
    public PluginServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void installPlugin(MultipartFile file) {
        storageService.store(file);
    }
}
