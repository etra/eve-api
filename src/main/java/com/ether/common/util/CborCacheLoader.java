package com.ether.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

public class CborCacheLoader {

    private static final ObjectMapper CBOR_MAPPER = new ObjectMapper(new CBORFactory());

    public static boolean exists(String cachePath, String name) {
        return Files.exists(toPath(cachePath, name));
    }

    public static <T> Map<String, T> load(String cachePath, String name, JavaType mapType) {
        Path path = toPath(cachePath, name);
        try (InputStream in = Files.newInputStream(path)) {
            return CBOR_MAPPER.readValue(in, mapType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load CBOR cache for " + name, e);
        }
    }

    public static <T> void save(String cachePath, String name, Map<String, T> data) {
        Path path = toPath(cachePath, name);
        try {
            Files.createDirectories(Path.of(cachePath));
            try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                CBOR_MAPPER.writeValue(out, data);
            }
        } catch (IOException e) {
            System.err.println("Failed to save CBOR cache for " + name + ": " + e.getMessage());
        }
    }

    private static Path toPath(String cachePath, String name) {
        return Path.of(cachePath).resolve(name.replace("/", "_") + ".cbor");
    }
}
