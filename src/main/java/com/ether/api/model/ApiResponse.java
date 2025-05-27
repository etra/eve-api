package com.ether.api.model;

import java.time.Instant;

public class ApiResponse<T> {
    private final T data;
    private final String message;
    private final Instant timestamp;
    private final boolean success;

    private ApiResponse(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.timestamp = Instant.now();
        this.success = success;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "Success", true);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, true);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, false);
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }
} 