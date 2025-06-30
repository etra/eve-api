package com.ether.api.view;

import java.util.ArrayList;
import java.util.List;

import com.ether.sde.model.TypeEntity;
import com.ether.sde.model.mining.optimization.OrePlan;

public class OptimizationOreView {
    private OrePlan orePlan;

    public OptimizationOreView(OrePlan orePlan) {
        this.orePlan = orePlan;
    }

    public String getId() {
        return orePlan.getOre().getOre().getEntityId();
    }

    public String getName() {
        return orePlan.getOre().getOre().getDisplayName();
    }

    public int getCycles() {
        return (int) orePlan.getCyclesNeeded();
    }

    public double getVolume() {
        return orePlan.getTotalVolume();
    }

    public int getItems() {
        return (int) orePlan.getTotalItems();
    }

    public List<OptimizationMineralView> getMinerals() {
        List<OptimizationMineralView> minerals = new ArrayList<>();
        orePlan.getMineralsProvided().forEach((entityId, quantity) -> {
            TypeEntity entity = orePlan.getOre().getMinerals().get(entityId).getMineral();
            if (entity != null) {
                minerals.add(new OptimizationMineralView(entity, quantity));
            }
        });


        return minerals;
    }

    // public List<OptimizationMineralView> getMinerals() {
    //     return orePlan.getMineralsProvided().entrySet().stream().map(OptimizationMineralView::new).collect(Collectors.toList());
    // }
    
}