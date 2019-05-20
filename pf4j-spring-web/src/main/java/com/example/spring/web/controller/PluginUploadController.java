package com.example.spring.web.controller;

import java.net.URI;

import com.example.spring.web.service.PluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Controller
@RequestMapping("/plugins")
public class PluginUploadController {

    private PluginService pluginService;

    @Autowired
    public PluginUploadController(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity handlePluginUpload(@RequestParam("file") MultipartFile file) {
        log.info("Uploading file: {}", file.getOriginalFilename());
        String pluginId = pluginService.installPlugin(file);
        return ResponseEntity.created(buildUri(pluginId)).build();
    }

    private URI buildUri(String pluginId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{pluginId}")
                .buildAndExpand(pluginId)
                .toUri();
    }
}
