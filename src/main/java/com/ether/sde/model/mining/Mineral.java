package com.ether.sde.model.mining;

import com.ether.sde.model.TypeEntity;

public class Mineral {
    private Ore ore;
    private TypeEntity mineral;
    private double quantity; //per 100 items of ore


    public Mineral(Ore ore, TypeEntity mineral, double quantity) {
        this.ore = ore;
        this.mineral = mineral;
        this.quantity = quantity;
    }

    public TypeEntity getMineral() {
        return mineral;
    }

    public double getQuantity() {
        return Math.floor(quantity * ore.getProcessingEfficiency());
    }

    public double getQuantityPer1m3() {
        return quantity / ore.getOre().getVolume() / 100;
    }

    public int getTypeID() {
        return Integer.parseInt(this.mineral.getEntityId());
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
