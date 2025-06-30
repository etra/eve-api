package com.ether.api.view;

import com.ether.sde.model.TypeEntity;

public class OptimizationMineralView {
    private ItemView entity;
    private double items;

    public OptimizationMineralView(TypeEntity entity, double items) {
        this.entity = new ItemView(entity);
        this.items = items;
    }

    public ItemView getEntity() {
        return entity;
    }

    public int getItems() {
        return (int) items;
    }

    
}