package com.ether.api.view;

import java.util.ArrayList;
import java.util.List;

import com.ether.sde.model.mining.Ore;

public class OreView {
    private int typeID;
    private String name;
    private List<MineralView> minerals;
    private String groupName;

    public OreView(Ore ore) {
        this.typeID = ore.getTypeID();
        this.name = ore.getOre().getDisplayName();
        this.minerals = new ArrayList<>();
        this.groupName = ore.getGroup().getDisplayName();

        ore.getMinerals().forEach((key, mineral) -> {
            this.minerals.add(new MineralView(mineral));
        });
    }

    public String getGroupName() {
        return this.groupName;
    }

    public int getTypeID() {
        return typeID;
    }

    public String getName() {
        return name;
    }

    public List<MineralView> getMinerals() {
        return minerals;
    }

}
