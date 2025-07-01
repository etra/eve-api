package com.ether.sde.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ether.sde.loader.YamlLoadService;
import com.ether.sde.model.Blueprint;
import com.ether.sde.model.CategoryEntity;
import com.ether.sde.model.GroupEntity;
import com.ether.sde.model.MarketGroupEntity;
import com.ether.sde.model.OreMinerals;
import com.ether.sde.model.TypeEntity;
import com.ether.sde.model.TypeMaterial;
import com.ether.sde.model.mining.Mineral;
import com.ether.sde.model.mining.Ore;

import jakarta.annotation.PostConstruct;
/**
 * Veldspar: 1230
Tritanium: 34



 */
@Component
public class SdeCache {

    private final YamlLoadService yamlLoader;
    
    private Map<String, GroupEntity> groups;
    private Map<String, TypeEntity> types;
    private Map<String, CategoryEntity> categories;
    private Map<String, MarketGroupEntity> marketGroups;
    private Map<String, Blueprint> blueprints;
    private Map<String, TypeMaterial> typeMaterials;

    private HashMap<String, String> itemsNameToKey;
    private HashMap<String, Blueprint> itemBlueprint;
    private HashMap<String, OreMinerals> oreMinerals;
    private HashMap<String, Ore> oreMining;


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
        typeMaterials = yamlLoader.load("fsd/typeMaterials.yaml", TypeMaterial.class);
        
        generateIndexes();
    }

    private void generateOreMining() {
        oreMining = new HashMap<>();

        this.types.values().forEach(oreType -> {
            if (oreType.isPublished() == false) {
                return;
            }
            GroupEntity groupEntity = groups.get(String.valueOf(oreType.getGroupID()));
            if (groupEntity == null) {
                return;
            }
            if (groupEntity.isPublished() == false) {
                return;
            }
            if (groupEntity.getCategoryID() != 25) {
                return;
            }
            TypeMaterial typeMaterial = typeMaterials.get(oreType.getEntityId());
            if (typeMaterial == null) {
                return;
            }
            if (typeMaterial.getMaterials().isEmpty()) {
                return;
            }
            
            Ore ore = new Ore(groupEntity, oreType);  
            oreMining.put(oreType.getEntityId(), ore);            

            typeMaterial.getMaterials().forEach(material -> {
                TypeEntity mineralEntity = types.get(String.valueOf(material.materialTypeID));
                if (mineralEntity == null) {
                    return;
                }
                if (mineralEntity.isPublished() == false) {
                    return;
                }
                ore.addMineral(new Mineral(ore, mineralEntity, material.quantity));
            });
        });
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
        
        generateOreMining();
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

    public Map<String, Ore> getOreMining() {
        return oreMining;
    }
}
