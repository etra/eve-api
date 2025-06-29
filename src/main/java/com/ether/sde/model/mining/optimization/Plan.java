package com.ether.sde.model.mining.optimization;

import java.util.List;
import java.util.Map;

public class Plan {
    private String type; // e.g. Single Ore, Multi Ore
    private String description;
    private double totalCycles;
    private double totalVolume;
    private double totalItems;
    private Map<Integer, Double> mineralsProvided;
    private List<OrePlan> orePlans;

    public Plan(String type, String description, double totalCycles, double totalVolume,
                double totalItems, Map<Integer, Double> mineralsProvided, List<OrePlan> orePlans) {
        this.type = type;
        this.description = description;
        this.totalCycles = totalCycles;
        this.totalVolume = totalVolume;
        this.totalItems = totalItems;
        this.mineralsProvided = mineralsProvided;
        this.orePlans = orePlans;
    }

    public String getType() { return type; }
    public String getDescription() { return description; }
    public double getTotalCycles() { return totalCycles; }
    public double getTotalVolume() { return totalVolume; }
    public double getTotalItems() { return totalItems; }
    public Map<Integer, Double> getMineralsProvided() { return mineralsProvided; }
    public List<OrePlan> getOrePlans() { return orePlans; }

    @Override
    public String toString() {
        return String.format("%s (%s) -> %.0f cycles, %.0f m³, %.0f items mined, minerals: %s",
            type, description, totalCycles, totalVolume, totalItems, mineralsProvided);
    }
}

