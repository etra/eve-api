package com.ether.sde.model;
/**
{
    "ore": {OreMinerals},
    "quantity": {quantity}
}

 */
public class MiningStrategy {
    private OreMinerals ore;
    private int quantity;

    public MiningStrategy(OreMinerals ore, int quantity) {
        this.ore = ore;
        this.quantity = quantity;
    }

    public OreMinerals getOre() {
        return ore;
    }

    public int getQuantity() {
        return quantity;
    }
}
