package com.ether.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ether.api.model.ApiResponse;
import com.ether.api.view.IndustryItemView;
import com.ether.api.view.IndustryView;
import com.ether.sde.model.Blueprint;
import com.ether.sde.model.TypeEntity;
import com.ether.sde.service.IndustryService;
import com.ether.sde.service.ItemService;

@RestController
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;
    private final ItemService itemService;

    public IndustryController(IndustryService industryService, ItemService itemService) {
        this.industryService = industryService;
        this.itemService = itemService;
    }

    @GetMapping("/requirements/{id}")
    public ResponseEntity<ApiResponse<IndustryView>> getRequirements(@PathVariable String id, @RequestParam(required = false) Integer runs, @RequestParam(required = false) Integer me, @RequestParam(required = false) Double materialBonus) {
        materialBonus = materialBonus == null ? 1.0 : materialBonus;
        runs = runs == null ? 1 : runs;
        me = me == null ? 0 : me;

        if (runs < 1) {
            return ResponseEntity.badRequest().body(ApiResponse.error(null, "Runs must be greater than 0"));
        }

        if (me < 0) {
            return ResponseEntity.badRequest().body(ApiResponse.error(null, "ME level must be greater than 0"));
        }

        try {
            List<Blueprint.Material> materials = this.industryService.getItemManufacturingRequirements(id, runs, me, materialBonus);
            TypeEntity entity = this.itemService.getItem(id);

            IndustryView view = new IndustryView(entity, materials, runs, me);

            return ResponseEntity.ok(ApiResponse.success(view));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(null, e.getMessage()));
        }

    }

    @GetMapping("/item/{id}")
    public ResponseEntity<ApiResponse<IndustryItemView>> getIndustryItem(@PathVariable String id) {
        String activityType = this.industryService.getItemActivityType(id);
        TypeEntity entity = this.itemService.getItem(id);
        return ResponseEntity.ok(ApiResponse.success(new IndustryItemView(id, entity.getDisplayName(), activityType)));
    }

    
    // @GetMapping
    // public ResponseEntity<ApiResponse<?>> getAllItems() {
    //     // return ResponseEntity.ok(ApiResponse.success(null, "Items retrieved successfully"));
    // }
} 