package com.ether.sde.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ether.sde.model.mining.Mineral;
import com.ether.sde.model.mining.Ore;
import com.ether.sde.model.mining.optimization.OrePlan;
import com.ether.sde.model.mining.optimization.Plan;
import com.ether.sde.model.mining.optimization.Response;

public class MiningOptimizer {

    public Response calculateMiningStrategies(
            Map<Integer, Double> requestedMinerals,
            Collection<Ore> ores,
            double miningYieldPerCycle
    ) {
        List<Integer> unfulfillable = findUnfulfillableMinerals(requestedMinerals, ores);
        Map<Integer, Double> fulfillableMinerals = requestedMinerals.entrySet().stream()
                .filter(e -> !unfulfillable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Plan> plans = new ArrayList<>();
        plans.addAll(buildSingleOrePlans(fulfillableMinerals, ores, miningYieldPerCycle));

        Plan multiPlan = buildMultiOrePlan(fulfillableMinerals, ores, miningYieldPerCycle);
        if (multiPlan != null) {
            plans.add(multiPlan);
        }

        plans.sort(Comparator.comparingDouble(plan -> ((Plan) plan).getTotalCycles())
                .thenComparing(Comparator.comparingDouble(plan -> ((Plan) plan).getTotalItems()).reversed()));

        List<Plan> topPlans = plans.subList(0, Math.min(5, plans.size()));

        return new Response(unfulfillable, topPlans);
    }

    private List<Integer> findUnfulfillableMinerals(Map<Integer, Double> requestedMinerals, Collection<Ore> ores) {
        List<Integer> unfulfillable = new ArrayList<>();
        for (Integer mineralID : requestedMinerals.keySet()) {
            boolean canBeProduced = ores.stream()
                    .anyMatch(ore -> ore.getMinerals().containsKey(mineralID));
            if (!canBeProduced) {
                unfulfillable.add(mineralID);
            }
        }
        return unfulfillable;
    }

    private List<Plan> buildSingleOrePlans(
            Map<Integer, Double> requestedMinerals,
            Collection<Ore> ores,
            double miningYieldPerCycle
    ) {
        List<Plan> plans = new ArrayList<>();
        for (Ore ore : ores) {
            boolean canFulfillAll = requestedMinerals.keySet().stream()
                    .allMatch(mineralID -> ore.getMinerals().containsKey(mineralID));

            if (!canFulfillAll) continue;

            double maxCycles = 0;
            Map<Integer, Double> mineralsProvided = new HashMap<>();

            for (Map.Entry<Integer, Double> req : requestedMinerals.entrySet()) {
                int mineralID = req.getKey();
                double qtyNeeded = req.getValue();
                Mineral mineral = ore.getMinerals().get(mineralID);

                double mineralPerCycle = mineral.getQuantityPer1m3() * miningYieldPerCycle;
                double cyclesForThis = Math.ceil(qtyNeeded / mineralPerCycle);
                maxCycles = Math.max(maxCycles, cyclesForThis);

                mineralsProvided.put(mineralID, cyclesForThis * mineralPerCycle);
            }

            double totalVolume = maxCycles * miningYieldPerCycle;
            double totalItems = totalVolume / ore.getOre().getVolume();
            OrePlan orePlan = new OrePlan(ore, maxCycles, totalVolume, totalItems, mineralsProvided);

            plans.add(new Plan(
                    "Single Ore",
                    "Mining only " + ore.getOre().getDisplayName(),
                    maxCycles, totalVolume, totalItems, mineralsProvided,
                    List.of(orePlan)
            ));
        }
        return plans;
    }

    private Plan buildMultiOrePlan(
            Map<Integer, Double> requestedMinerals,
            Collection<Ore> ores,
            double miningYieldPerCycle
    ) {
        Map<Integer, Double> remainingNeeds = new HashMap<>(requestedMinerals);
        List<OrePlan> orePlans = new ArrayList<>();
        int iteration = 0;
        while (remainingNeeds.values().stream().anyMatch(q -> q > 0) && iteration < 10) {
            System.out.println("Iteration: " + iteration);
            System.out.println("Remaining needs: " + remainingNeeds);
            Ore bestOre = findBestOre(remainingNeeds, ores);
            System.out.println("Best ore: " + bestOre.getOre().getDisplayName());
            if (bestOre == null) break;

            OrePlan orePlan = computeOrePlan(bestOre, remainingNeeds, miningYieldPerCycle);
            orePlans.add(orePlan);

            for (Map.Entry<Integer, Double> e : orePlan.getMineralsProvided().entrySet()) {
                remainingNeeds.compute(e.getKey(), (k, v) -> Math.max(0, v - e.getValue()));
            }
            iteration++;
        }

        if (remainingNeeds.values().stream().anyMatch(q -> q > 0)) {
            return null; // could not fulfill everything
        }

        double totalCycles = orePlans.stream().mapToDouble(OrePlan::getCyclesNeeded).sum();
        double totalVolume = orePlans.stream().mapToDouble(OrePlan::getTotalVolume).sum();
        double totalItems = orePlans.stream().mapToDouble(OrePlan::getTotalItems).sum();

        Map<Integer, Double> mineralsProvided = orePlans.stream()
                .flatMap(plan -> plan.getMineralsProvided().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Double::sum
                ));

        return new Plan("Multi Ore", "Mix of ores to fulfill needs",
                totalCycles, totalVolume, totalItems, mineralsProvided, orePlans);
    }

    private Ore findBestOre(Map<Integer, Double> remainingNeeds, Collection<Ore> ores) {
        System.out.println("Finding best ore");
        System.out.println("Remaining needs: " + remainingNeeds);
        System.out.println("Ores: " + ores);
        return ores.stream()
                .min(Comparator.comparingDouble(ore -> {
                    double score = 0;
                    for (Map.Entry<Integer, Double> req : remainingNeeds.entrySet()) {
                        if (req.getValue() == 0) continue;
                        Mineral m = ore.getMinerals().get(req.getKey());
                        if (m != null && m.getQuantityPer1m3() > 0) {
                            score -= m.getQuantityPer1m3() / req.getValue();
                        }
                    }
                    return score;
                }))
                .orElse(null);
    }

    private OrePlan computeOrePlan(Ore ore, Map<Integer, Double> remainingNeeds, double miningYieldPerCycle) {
        double maxCycles = 0;
        Map<Integer, Double> mineralsProvided = new HashMap<>();

        for (Map.Entry<Integer, Double> req : remainingNeeds.entrySet()) {
            int mineralID = req.getKey();
            double qtyNeeded = req.getValue();
            Mineral m = ore.getMinerals().get(mineralID);

            if (m != null && m.getQuantityPer1m3() > 0) {
                double mineralPerCycle = m.getQuantityPer1m3() * miningYieldPerCycle;
                double cyclesForThis = Math.ceil(qtyNeeded / mineralPerCycle);
                maxCycles = Math.max(maxCycles, cyclesForThis);

                mineralsProvided.put(mineralID, cyclesForThis * mineralPerCycle);
            }
        }

        double totalVolume = maxCycles * miningYieldPerCycle;
        double totalItems = totalVolume / ore.getOre().getVolume();

        return new OrePlan(ore, maxCycles, totalVolume, totalItems, mineralsProvided);
    }
}
