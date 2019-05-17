package com.example.spring.web.service;

import org.springframework.web.multipart.MultipartFile;

public interface PluginService {
    void installPlugin(MultipartFile file);
}
