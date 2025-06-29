package com.ether.sde.model.mining.optimization;

import java.util.List;

public class Response {
    private List<Integer> unfulfillableMinerals;
    private List<Plan> topPlans;

    public Response(List<Integer> unfulfillableMinerals, List<Plan> topPlans) {
        this.unfulfillableMinerals = unfulfillableMinerals;
        this.topPlans = topPlans;
    }

    public List<Integer> getUnfulfillableMinerals() { return unfulfillableMinerals; }
    public List<Plan> getTopPlans() { return topPlans; }
}
