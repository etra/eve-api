package com.ether.sde.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<TypeEntity> searchItemsByName(String query) {
        final String lowered = query.toLowerCase();
        return this.cache.getItemsNameToKey().entrySet().stream()
            .filter(entry -> entry.getKey().contains(lowered))
            .sorted(Comparator.comparingInt(entry -> entry.getKey().length()))
            .limit(50)
            .map(entry -> this.cache.getType(entry.getValue()))
            .collect(Collectors.toList());
    }
}
