package com.ether.sde.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;

import com.ether.common.util.CborCacheLoader;
import com.ether.config.SdeProperties;
import com.ether.sde.model.HasEntityId;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

import jakarta.annotation.PreDestroy;

@Component
public class YamlLoadService {

    private final ObjectMapper yamlMapper;
    private final ZipFile zipFile;
    private final SdeProperties sdeProperties;

    private static final Set<String> allowedFiles = Set.of(
        "fsd/groups.yaml",
        "fsd/types.yaml",
        "fsd/categories.yaml",
        "fsd/marketGroups.yaml",
        "fsd/blueprints.yaml"
    );

    public YamlLoadService(SdeProperties sdeProperties) throws IOException {
        this.sdeProperties = sdeProperties;
        int pointLimit = 1000 * 1024 * 1024; // 1GB
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setCodePointLimit(pointLimit);

        YAMLFactory yamlFactory = YAMLFactory.builder()
            .loaderOptions(loaderOptions)
            .build();

        yamlMapper = new ObjectMapper(yamlFactory);
        yamlMapper.getFactory().setStreamReadConstraints(
            StreamReadConstraints.builder().build()
        );

        this.zipFile = new ZipFile(this.sdeProperties.getPath());
    }

    @PreDestroy
    public void closeZipFile() {
        if (this.zipFile != null) {
            try {
                this.zipFile.close();
                System.out.println("ZipFile closed successfully.");
            } catch (IOException e) {
                System.err.println("Error closing ZipFile: " + e.getMessage());
            }
        }
    }

    public <T> Map<String, T> load(String name, Class<T> valueType) {
        String cachePath = this.sdeProperties.getCbor().getCache().getPath();
        // if (!allowedFiles.contains(name)) {
        //     throw new RuntimeException("YAML file not allowed for loading: " + name);
        // }

        ZipEntry entry = zipFile.getEntry(name);
        if (entry == null) {
            throw new RuntimeException("YAML file not found: " + name);
        }

        JavaType mapType = yamlMapper.getTypeFactory().constructMapType(Map.class, String.class, valueType);

        if (CborCacheLoader.exists(cachePath, name)) {
            try {
                return CborCacheLoader.load(cachePath, name, mapType);
            } catch (Exception e) {
                System.out.println("Failed to load CBOR cache for " + name + ", deleted corrupted cache");
                CborCacheLoader.delete(cachePath, name);
            }
            
        }

        Map<String, T> result = new HashMap<>();

        try (InputStream is = zipFile.getInputStream(entry);
             YAMLParser parser = (YAMLParser) yamlMapper.getFactory().createParser(is)) {

            if (parser.nextToken() != JsonToken.START_OBJECT) {
                throw new RuntimeException("Expected a YAML object (map) in file: " + name);
            }

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String key = parser.currentName();
                parser.nextToken(); // move to value

                T value = yamlMapper.readerFor(valueType).readValue(parser);

                if (HasEntityId.class.isAssignableFrom(valueType)) {
                    ((HasEntityId) value).setEntityId(key);
                }

                result.put(key, value);
            }

            CborCacheLoader.save(cachePath, name, result);

        } catch (IOException e) {
            throw new RuntimeException("Failed to stream parse YAML file: " + name, e);
        }

        return result;
    }
}
