package com.ether.sde.model.mining;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.ether.sde.model.GroupEntity;
import com.ether.sde.model.TypeEntity;

public class Ore {
    private TypeEntity ore;
    private GroupEntity group;
    private HashMap<Integer, Mineral> minerals;
    private double oreReprocessingEfficiency = 1;
    private double gasReprocessingEfficiency = 1;

    public Ore(GroupEntity group, TypeEntity ore) {
        this.group = group;
        this.ore = ore;
        this.minerals = new HashMap<>();
    }

    public void setOreReprocessingEfficiency(double oreReprocessingEfficiency) {
        this.oreReprocessingEfficiency = oreReprocessingEfficiency;
    }

    public void setGasReprocessingEfficiency(double gasReprocessingEfficiency) {
        this.gasReprocessingEfficiency = gasReprocessingEfficiency;
    }

    public double getProcessingEfficiency() {
        if (group.getDisplayName().toLowerCase().contains("gas")) {
            return gasReprocessingEfficiency;
        }
        return oreReprocessingEfficiency;
    }


    public TypeEntity getOre() {
        return ore;
    }

    public HashMap<Integer, Mineral> getMinerals() {
        return minerals;
    }

    public void addMineral(Mineral mineral) {
        this.minerals.put(mineral.getTypeID(), mineral);
    }

    public int getTypeID() {
        return Integer.parseInt(this.ore.getEntityId());
    }

    public GroupEntity getGroup() {
        return group;
    }

    public List<Integer> getMineralTypeIDs() {
        return this.minerals.keySet().stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
    }
}
