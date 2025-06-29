package com.ether.api.view;

import com.ether.sde.model.mining.Mineral;

public class MineralView {
    private int typeID;
    private String name;
    private double quantity; // quantity per 100 units of ore

    public double getQuantity() {
        return quantity;
    }

    public MineralView(Mineral mineral) {
        this.typeID = mineral.getTypeID();
        this.name = mineral.getMineral().getDisplayName();
        this.quantity = mineral.getQuantity();
    }

    public int getTypeID() {
        return typeID;
    }

    public String getName() {
        return name;
    }
}
