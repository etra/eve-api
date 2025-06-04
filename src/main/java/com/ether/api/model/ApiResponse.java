package com.ether.api.model;

import java.time.Instant;

public class ApiResponse<T> {
    private final T response;
    private final Instant timestamp;
    private final boolean success;
    private final String error;

    private ApiResponse(T response, boolean success, String error) {
        this.response = response;
        this.error = error;
        this.timestamp = Instant.now();
        this.success = success;
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(response, true, null);
    }

    public static <T> ApiResponse<T> error(T response, String error) {
        return new ApiResponse<>(null, false, error);
    }

    public T getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }
} 