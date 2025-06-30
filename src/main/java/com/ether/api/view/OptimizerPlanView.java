package com.ether.api.view;

import java.util.List;
import java.util.stream.Collectors;

import com.ether.sde.model.mining.optimization.Plan;

public class OptimizerPlanView {
    private Plan plan;

    public OptimizerPlanView(Plan plan) {
        this.plan = plan;
    }

    public String getType() {
        return plan.getType();
    }

    public String getDescription() {
        return plan.getDescription();
    }

    public int getTotalCycles() {
        return (int) plan.getTotalCycles();
    }

    public double getTotalVolume() {
        return plan.getTotalVolume();
    }

    public int getTotalItems() {
        return (int) plan.getTotalItems();
    }

    public List<OptimizationOreView> getOre() {
        return this.plan.getOrePlans().stream().map(OptimizationOreView::new).collect(Collectors.toList());
    }
}
