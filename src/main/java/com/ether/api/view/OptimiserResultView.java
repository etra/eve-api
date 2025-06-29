// package com.ether.api.view;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import com.ether.sde.utils.MiningOptimizer;


// public class OptimiserResultView {
//     private Map<Integer, Double> requestedMinerals;
//     private double miningYieldPerCycle;
//     private List<OptimiserPlanView> plans;
//     private List<Integer> unfulfillableMinerals;

//     public OptimiserResultView(Map<Integer, Double> requestedMinerals, double miningYieldPerCycle, MiningOptimizer.OptimizationResult result) {
//         this.requestedMinerals = requestedMinerals;
//         this.miningYieldPerCycle = miningYieldPerCycle;
//         this.plans = new ArrayList<>();
//         for (MiningOptimizer.Plan plan : result.getPlans()) {
//             this.plans.add(new OptimiserPlanView(plan));
//         }
//         this.unfulfillableMinerals = result.getUnfulfillableMinerals();
//     }

//     public Map<Integer, Double> getRequestedMinerals() {
//         return requestedMinerals;
//     }

//     public double getMiningYieldPerCycle() {
//         return miningYieldPerCycle;
//     }

//     public List<Integer> getUnfulfillableMinerals() {
//         return unfulfillableMinerals;
//     }

//     public List<OptimiserPlanView> getPlans() {
//         return this.plans;
//     }


// }
