package com.ether.sde.model;

import java.util.HashMap;

public class OreMining {
    OreMinerals ore;
    HashMap<Integer, Double> mineralYields;

    public OreMining(OreMinerals ore, HashMap<Integer, Double> mineralYields) {
        this.mineralYields = mineralYields;
        this.ore = ore;
    }

    public OreMinerals getOre() {
        return ore;
    }

    public HashMap<Integer, Double> getMineralYields() {
        return mineralYields;
    }
}
