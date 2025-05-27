package com.ether.sde.service;

import org.springframework.stereotype.Service;

import com.ether.sde.cache.SdeCache;
import com.ether.sde.model.Type;
import com.ether.sde.view.SearchResultView;

@Service
public class SearchService {

    private final SdeCache cache;

    public SearchService(SdeCache cache) {
        this.cache = cache;
    }


    public SearchResultView searchItemsOnly(String query) {
        System.out.println(this.cache.getItemsNameToKey());
        final String lowered = query.toLowerCase();
        System.out.println(lowered);
        if (this.cache.getItemsNameToKey().containsKey(lowered)) {
            System.out.println("found");
            String typeId = this.cache.getItemsNameToKey().get(lowered);
            Type type = this.cache.getType(typeId);
            
            return new SearchResultView(
                Integer.parseInt(typeId),
                type.getName().get("en"),
                this.cache.getMarketGroup(type.getMarketGroupID().toString()).getNameID().get("en")
            );
        }

        return null;
    }
}
