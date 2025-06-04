package com.ether.api.view;

import com.ether.sde.model.TypeEntity;

public class ItemView {
    private final int id;
    private final String name;


    public ItemView(TypeEntity item) {
        this.id = Integer.parseInt(item.getEntityId());
        this.name = item.getName().get("en");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
