package com.ether.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ether.api.model.ApiResponse;

@RestController
@RequestMapping("/items")
public class ItemController {

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getItem(@PathVariable Long id) {
        // TODO: Implement item retrieval
        return ResponseEntity.ok(ApiResponse.success(null, "Item retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllItems() {
        // TODO: Implement items list retrieval
        return ResponseEntity.ok(ApiResponse.success(null, "Items retrieved successfully"));
    }
} 