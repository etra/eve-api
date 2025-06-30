package com.ether.sde.service;

import org.springframework.stereotype.Service;

import com.ether.sde.cache.SdeCache;
import com.ether.sde.model.TypeEntity;

@Service
public class ItemService {

    private final SdeCache cache;

    public ItemService(SdeCache cache) {
        this.cache = cache;
    }

    public TypeEntity getItem(String typeId) {
        return this.cache.getType(typeId);
    }

    public String getItemKeyByName(String name) {
        return this.cache.getItemsNameToKey().get(name.toLowerCase());
    }
}
