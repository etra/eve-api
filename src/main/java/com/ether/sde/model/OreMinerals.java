package com.ether.sde.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OreMinerals {
    private String typeID;
    private String name;
    private double volume;
    private HashMap<Integer, Mineral> minerals;
    private List<Integer> mineralTypeIDs;
    private int groupID;

    public OreMinerals(String typeID, String name, double volume, int groupID) {
        this.typeID = typeID;
        this.name = name;
        this.volume = volume;
        this.groupID = groupID;
        this.minerals = new HashMap<Integer, Mineral>();
        this.mineralTypeIDs = new ArrayList<Integer>();
    }

    public static class Mineral {
        public int typeID;
        public String name;
        public int quantity;
        public double volume;

        public Mineral(int typeID, String name, int quantity, double volume) {
            this.typeID = typeID;
            this.name = name;
            this.quantity = quantity;
            this.volume = volume;
        }

        public int getTypeID() {
            return typeID;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getVolume() {
            return volume;
        }
    }


    public HashMap<Integer, Mineral> getMinerals() {
        return minerals;
    }

    public void setMinerals(HashMap<Integer, Mineral> minerals) {
        this.minerals = minerals;
    }

    public void addMineral(Mineral mineral) {
        this.minerals.put(mineral.getTypeID(), mineral);
        this.mineralTypeIDs.add(mineral.getTypeID());
    }

    public String getTypeID() {
        return typeID;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public List<Integer> getMineralTypeIDs() {
        return mineralTypeIDs;
    }

    public int getGroupID() {
        return groupID;
    }

}
