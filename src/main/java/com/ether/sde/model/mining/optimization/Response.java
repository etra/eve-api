package com.ether.sde.model.mining.optimization;

import java.util.List;

import com.ether.sde.model.TypeEntity;


public class Response {
    private List<Integer> unfulfillableMinerals;
    private List<TypeEntity> unfulfillableEntities;
    private List<Plan> topPlans;

    public Response(List<Integer> unfulfillableMinerals, List<Plan> topPlans) {
        this.unfulfillableMinerals = unfulfillableMinerals;
        this.topPlans = topPlans;
    }

    public List<Integer> getUnfulfillableMinerals() { return unfulfillableMinerals; }
    public List<Plan> getTopPlans() { return topPlans; }
    public List<TypeEntity> getUnfulfillableEntities() { return unfulfillableEntities; }
    public void setUnfulfillableEntities(List<TypeEntity> unfulfillableEntities) { this.unfulfillableEntities = unfulfillableEntities; }
}
