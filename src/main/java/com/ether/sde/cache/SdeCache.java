package com.ether.sde.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ether.sde.loader.YamlLoadService;
import com.ether.sde.model.Blueprint;
import com.ether.sde.model.CategoryEntity;
import com.ether.sde.model.GroupEntity;
import com.ether.sde.model.MarketGroupEntity;
import com.ether.sde.model.TypeEntity;

import jakarta.annotation.PostConstruct;

@Component
public class SdeCache {

    private final YamlLoadService yamlLoader;
    
    private Map<String, GroupEntity> groups;
    private Map<String, TypeEntity> types;
    private Map<String, CategoryEntity> categories;
    private Map<String, MarketGroupEntity> marketGroups;
    private Map<String, Blueprint> blueprints;

    private HashMap<String, String> itemsNameToKey;
    private HashMap<String, Blueprint> itemBlueprint;

    public SdeCache(YamlLoadService yamlLoader) {
        this.yamlLoader = yamlLoader;
    }

    @PostConstruct
    public void init() {
        groups = yamlLoader.load("fsd/groups.yaml", GroupEntity.class);
        types = yamlLoader.load("fsd/types.yaml", TypeEntity.class);
        categories = yamlLoader.load("fsd/categories.yaml", CategoryEntity.class);
        marketGroups = yamlLoader.load("fsd/marketGroups.yaml", MarketGroupEntity.class);
        blueprints = yamlLoader.load("fsd/blueprints.yaml", Blueprint.class);
        
        generateIndexes();
    }


    private void generateIndexes() {
        itemsNameToKey = new HashMap<>();
        types.values().stream()
        .filter(item -> item.isPublished())
        .filter(type -> type.getMarketGroupID() != null)
        .forEach(item -> {
            itemsNameToKey.put(item.getName().get("en").toLowerCase(), item.getEntityId());
        });

        itemBlueprint = new HashMap<>();
        blueprints.values().forEach(blueprint -> {
            if (blueprint.getManufacturingProduct() != null) {
                String key = String.valueOf(blueprint.getManufacturingProduct().typeID);
                itemBlueprint.put(key, blueprint);
            }
            if (blueprint.getReactionProduct() != null) {
                String key = String.valueOf(blueprint.getReactionProduct().typeID);
                itemBlueprint.put(key, blueprint);
            }
        });
    }

    public TypeEntity getType(String id) {
        return types.get(id);
    }

    public MarketGroupEntity getMarketGroup(String id) {
        return marketGroups.get(id);
    }

    public Blueprint getBlueprint(String id) {
        return blueprints.get(id);
    }

    public Map<String, TypeEntity> getTypes() {
        return types;
    }

    public Map<String, GroupEntity> getGroups() {
        return groups;
    }

    public Map<String, MarketGroupEntity> getMarketGroups() {
        return marketGroups;
    }

    public Map<String, CategoryEntity> getCategories() {
        return categories;
    }

    public Map<String, Blueprint> getBlueprints() {
        return blueprints;
    }

    public Map<String, String> getItemsNameToKey() {
        return itemsNameToKey;
    }

    public Map<String, Blueprint> getItemBlueprint() {
        return itemBlueprint;
    }
}
