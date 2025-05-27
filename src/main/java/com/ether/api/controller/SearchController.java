package com.ether.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ether.sde.service.SearchService;
import com.ether.sde.view.SearchResultView;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/items")
    public SearchResultView searchItemsOnly(@RequestParam("q") String query) {
        return searchService.searchItemsOnly(query);
    }
}
