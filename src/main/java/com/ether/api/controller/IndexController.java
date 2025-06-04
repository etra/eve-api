package com.ether.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ether.api.model.ApiResponse;

@RestController
@RequestMapping("/")
public class IndexController  {

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getIndex() {
        return ResponseEntity.ok(ApiResponse.success("Hello, World!"));
    }
} 