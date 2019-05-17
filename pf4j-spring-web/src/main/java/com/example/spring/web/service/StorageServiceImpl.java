package com.example.spring.web.service;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.example.spring.web.exception.StorageException;
import com.example.spring.web.exception.StorageFileNotFoundException;
import com.example.spring.web.properties.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            log.info("Initializing storage in location: {}", rootLocation.toAbsolutePath());
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Path store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = rootLocation.resolve(filename);

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory " + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, REPLACE_EXISTING);
            }
        } catch (IOException e) {
            log.error("Failed to store file {} in location: {}", filename, targetLocation.toAbsolutePath(), e);
            throw new StorageException("Failed to store file " + filename, e);
        }

        return targetLocation;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize);
        } catch (IOException e) {
            log.error("Failed to read stored files in location: {}", rootLocation.toAbsolutePath(), e);
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Path targetLocation = rootLocation.resolve(filename);
        try {
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Could not read file {} from URI location: {}", filename, targetLocation.toUri());
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            log.error("Could not read file {} from location: {}", filename, targetLocation.toAbsolutePath(), e);
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
