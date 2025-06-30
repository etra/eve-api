package com.ether.api.view;

import com.ether.sde.model.TypeEntity;

public class ItemView {
    private final int id;
    private final String name;
    private final double volume;

    public ItemView(TypeEntity item) {
        this.id = Integer.parseInt(item.getEntityId());
        this.name = item.getName().get("en");
        this.volume = item.getVolume();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }
}
