package com.ether.api.view;

import java.util.ArrayList;
import java.util.List;

import com.ether.sde.model.mining.Ore;

public class OreView {
    private int typeID;
    private String name;
    private double volume;
    private List<MineralView> minerals;
    private double reprocessingVolume;

    public OreView(Ore ore) {
        this.typeID = ore.getTypeID();
        this.name = ore.getOre().getDisplayName();
        this.volume = ore.getOre().getVolume();
        this.minerals = new ArrayList<>();
        this.reprocessingVolume = ore.getOre().getReprocessingVolume();

        ore.getMinerals().forEach((key, mineral) -> {
            this.minerals.add(new MineralView(mineral));
        });
    }

    public int getTypeID() {
        return typeID;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public double getReprocessingVolume() {
        return reprocessingVolume;
    }

    public List<MineralView> getMinerals() {
        return minerals;
    }

}
