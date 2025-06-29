package com.ether.api.view;

import java.util.List;
import java.util.Map;

public class MiningStrategyView {
    private Map<Integer, Double> unfulfilledMaterials;
    private Map<Integer, Double> requestedMinerals;
    private Map<Integer, Double> availableOres;
    private double miningYieldPerCycle;
    private int miningCycleTimeSeconds;
    private List<MiningPlanView> miningPlans;

    public static class MiningPlanView {
        private List<OreView> ores;

        public static class OreView {
            private int oreTypeID;
            private String oreName;
            private double unitsToMine;
            private double totalVolume;
            private double cyclesNeeded;
            private Map<Integer, Double> minedMinerals;

            public OreView(int oreTypeID, String oreName, double unitsToMine, double totalVolume, double cyclesNeeded, Map<Integer, Double> minedMinerals) {
                this.oreTypeID = oreTypeID;
                this.oreName = oreName;
                this.unitsToMine = unitsToMine;
                this.totalVolume = totalVolume;
                this.cyclesNeeded = cyclesNeeded;
                this.minedMinerals = minedMinerals;
            }

            public int getOreTypeID() {
                return oreTypeID;
            }

            public String getOreName() {
                return oreName;
            }

            public double getUnitsToMine() {
                return unitsToMine;
            }

            public double getTotalVolume() {
                return totalVolume;
            }

            public double getCyclesNeeded() {
                return cyclesNeeded;
            }

            public Map<Integer, Double> getMinedMinerals() {
                return minedMinerals;
            }
        }

        public MiningPlanView(List<OreView> ores) {
            this.ores = ores;
        }

        public List<OreView> getOres() {
            return ores;
        }
    }

    public MiningStrategyView(Map<Integer, Double> unfulfilledMaterials, Map<Integer, Double> requestedMinerals, Map<Integer, Double> availableOres, double miningYieldPerCycle, int miningCycleTimeSeconds, List<MiningPlanView> miningPlans) {
        this.unfulfilledMaterials = unfulfilledMaterials;
        this.requestedMinerals = requestedMinerals;
        this.availableOres = availableOres;
        this.miningYieldPerCycle = miningYieldPerCycle;
        this.miningCycleTimeSeconds = miningCycleTimeSeconds;
        this.miningPlans = miningPlans;
    }

    public Map<Integer, Double> getUnfulfilledMaterials() {
        return unfulfilledMaterials;
    }

    public Map<Integer, Double> getRequestedMinerals() {
        return requestedMinerals;
    }

    public Map<Integer, Double> getAvailableOres() {
        return availableOres;
    }

    public double getMiningYieldPerCycle() {
        return miningYieldPerCycle;
    }

    public int getMiningCycleTimeSeconds() {
        return miningCycleTimeSeconds;
    }

    public List<MiningPlanView> getMiningPlans() {
        return miningPlans;
    }
}


