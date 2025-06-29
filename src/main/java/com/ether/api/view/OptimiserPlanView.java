// package com.ether.api.view;

// import java.util.ArrayList;
// import java.util.List;

// import com.ether.sde.utils.MiningOptimizer;

// public class OptimiserPlanView {
//     private MiningOptimizer.Plan plan;
//     private List<OptimizationOreView> orePlans;

//     public OptimiserPlanView(MiningOptimizer.Plan plan) {
//         this.plan = plan;
//         this.orePlans = new ArrayList<>();
//         for (MiningOptimizer.OrePlan orePlan : plan.getOrePlans()) {
//             this.orePlans.add(new OptimizationOreView(orePlan));
//         }
//     }

//     public String getName() {
//         return plan.getPlanName();
//     }

//     public String getDescription() {
//         return plan.getDescription();
//     }

//     public List<OptimizationOreView> getOrePlans() {
//         return orePlans;
//     }

// }
