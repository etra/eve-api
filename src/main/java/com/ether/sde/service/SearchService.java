package com.ether.sde.service;

import org.springframework.stereotype.Service;

import com.ether.sde.cache.SdeCache;
import com.ether.sde.model.TypeEntity;

@Service
public class SearchService {

    private final SdeCache cache;

    public SearchService(SdeCache cache) {
        this.cache = cache;
    }


    public TypeEntity searchItemByName(String query) {
        final String lowered = query.toLowerCase();
        if (this.cache.getItemsNameToKey().containsKey(lowered)) {
            return this.cache.getType(this.cache.getItemsNameToKey().get(lowered));
        }

        return null;
    }
}
