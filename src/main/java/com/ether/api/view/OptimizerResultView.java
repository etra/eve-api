package com.ether.api.view;

import java.util.List;
import java.util.stream.Collectors;

import com.ether.sde.model.TypeEntity;
import com.ether.sde.model.mining.optimization.Plan;


public class OptimizerResultView {
    private List<ItemView> unfulfillableEntities;
    private List<Plan> topPlans;

    public OptimizerResultView(List<TypeEntity> unfulfillableEntities, List<Plan> topPlans) {
        this.unfulfillableEntities = unfulfillableEntities.stream().map(ItemView::new).collect(Collectors.toList());
        this.topPlans = topPlans;
    }

    public List<ItemView> getUnfulfillableEntities() {
        return unfulfillableEntities;
    }

    public List<OptimizerPlanView> getPlans() {
        return this.topPlans.stream().map(OptimizerPlanView::new).collect(Collectors.toList());
    }
}