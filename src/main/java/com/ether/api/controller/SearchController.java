package com.ether.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ether.api.model.ApiResponse;
import com.ether.api.view.ItemView;
import com.ether.sde.model.TypeEntity;
import com.ether.sde.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/item")
    public ResponseEntity<ApiResponse<ItemView>> searchItem(@RequestParam("q") String query) {
        try {
            TypeEntity type = this.searchService.searchItemByName(query);
            if (type == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ApiResponse.success(new ItemView(type)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/items")
    public ResponseEntity<ApiResponse<List<ItemView>>> searchItems(@RequestParam("q") String query) {
        try {
            List<TypeEntity> types = this.searchService.searchItemsByName(query);
            if (types.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.success(List.of()));
            }
            return ResponseEntity.ok(ApiResponse.success(types.stream().map(ItemView::new).collect(Collectors.toList())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        
    }
}
