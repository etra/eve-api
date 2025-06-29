package com.ether.sde.model.mining.optimization;

import java.util.Map;

import com.ether.sde.model.mining.Ore;

public class OrePlan {
    private Ore ore;
    private double cyclesNeeded;
    private double totalVolume;
    private double totalItems;
    private Map<Integer, Double> mineralsProvided;

    public OrePlan(Ore ore, double cyclesNeeded, double totalVolume, double totalItems, Map<Integer, Double> mineralsProvided) {
        this.ore = ore;
        this.cyclesNeeded = cyclesNeeded;
        this.totalVolume = totalVolume;
        this.totalItems = totalItems;
        this.mineralsProvided = mineralsProvided;
    }

    public Ore getOre() { return ore; }
    public double getCyclesNeeded() { return cyclesNeeded; }
    public double getTotalVolume() { return totalVolume; }
    public double getTotalItems() { return totalItems; }
    public Map<Integer, Double> getMineralsProvided() { return mineralsProvided; }

    @Override
    public String toString() {
        return String.format("%s -> %.0f cycles, %.0f m³, %.0f items, minerals: %s",
            ore.getOre().getDisplayName(), cyclesNeeded, totalVolume, totalItems, mineralsProvided);
    }
}
