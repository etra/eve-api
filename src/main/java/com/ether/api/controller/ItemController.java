package com.ether.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ether.api.model.ApiResponse;
import com.ether.api.view.ItemView;
import com.ether.sde.model.TypeEntity;
import com.ether.sde.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemView>> getItem(@PathVariable String id) {
        try {
            TypeEntity entity = this.itemService.getItem(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ApiResponse.success(new ItemView(entity)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    
    // @GetMapping
    // public ResponseEntity<ApiResponse<?>> getAllItems() {
    //     // return ResponseEntity.ok(ApiResponse.success(null, "Items retrieved successfully"));
    // }
} 