package com.ether.sde.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ether.sde.loader.YamlLoadService;
import com.ether.sde.model.Category;
import com.ether.sde.model.Group;
import com.ether.sde.model.MarketGroup;
import com.ether.sde.model.Type;

import jakarta.annotation.PostConstruct;


@Component
public class SdeCache {

    private final YamlLoadService yamlLoader;
    
    private Map<String, Group> groups;
    private Map<String, Type> types;
    private Map<String, Category> categories;
    private Map<String, MarketGroup> marketGroups;

    private HashMap<String, String> itemsNameToKey = new HashMap<>();


    public SdeCache(YamlLoadService yamlLoader) {
        this.yamlLoader = yamlLoader;
    }

    @PostConstruct
    public void init() {
        groups = yamlLoader.load("fsd/groups.yaml", Group.class);
        types = yamlLoader.load("fsd/types.yaml", Type.class);
        categories = yamlLoader.load("fsd/categories.yaml", Category.class);
        marketGroups = yamlLoader.load("fsd/marketGroups.yaml", MarketGroup.class);
        
        types.entrySet().stream()
        .filter(item -> item.getValue().isPublished())
        .filter(type -> type.getValue().getMarketGroupID() != null)
        .forEach(item -> {
            itemsNameToKey.put(item.getValue().getName().get("en").toLowerCase(), item.getKey());
        });

        System.out.println(itemsNameToKey);
    }

    
    public Group getGroup(String id) {
        return groups.get(id);
    }

    public Type getType(String id) {
        return types.get(id);
    }

    public MarketGroup getMarketGroup(String id) {
        return marketGroups.get(id);
    }

    public Map<String, Type> getTypes() {
        return types;
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public Map<String, MarketGroup> getMarketGroups() {
        return marketGroups;
    }

    public Map<String, String> getItemsNameToKey() {
        return itemsNameToKey;
    }
}
