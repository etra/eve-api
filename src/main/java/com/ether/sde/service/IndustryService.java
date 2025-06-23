package com.ether.sde.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ether.sde.cache.SdeCache;
import com.ether.sde.model.Blueprint;


@Service
public class IndustryService {

    private final SdeCache cache;

    public IndustryService(SdeCache cache) {
        this.cache = cache;
    }

    public List<Blueprint.Material> getItemManufacturingRequirements(String typeId, Integer runs, Integer meLevel) {
        Blueprint blueprint = this.cache.getItemBlueprint().get(typeId);
        if (blueprint == null) {
            return new ArrayList<>();
        }
        return blueprint.getMaterials(runs, meLevel);
    }

    public String getItemActivityType(String typeId) {
        Blueprint blueprint = this.cache.getItemBlueprint().get(typeId);
        if (blueprint == null) {
            return "raw";
        }
        return blueprint.getActivityType();
    }
        
}
