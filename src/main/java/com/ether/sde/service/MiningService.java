package com.ether.sde.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ether.sde.cache.SdeCache;
import com.ether.sde.model.mining.Ore;
import com.ether.sde.model.mining.optimization.Response;
import com.ether.sde.utils.MiningOptimizer;

@Service
public class MiningService {

    private final SdeCache cache;
    

    public MiningService(SdeCache cache) {
        this.cache = cache;
    }

    public List<Ore> getOreList(List<String> availableGroups, double oreReprocessingEfficiency, double gasReprocessingEfficiency) {
        // return this.cache.getOreMining().values().stream().collect(Collectors.toList());
        return this.filterOreList(availableGroups, oreReprocessingEfficiency, gasReprocessingEfficiency); 
    }


    private List<Ore> filterOreList(List<String> availableGroups, double oreReprocessingEfficiency, double gasReprocessingEfficiency) {
        List<Ore> filteredOreGroups = new ArrayList<>();
        
        this.cache.getOreMining().values().forEach(ore -> {
            if (availableGroups.contains(ore.getGroup().getEntityId()) || availableGroups.isEmpty()) {
                ore.setOreReprocessingEfficiency(oreReprocessingEfficiency);
                ore.setGasReprocessingEfficiency(gasReprocessingEfficiency);
                filteredOreGroups.add(ore);
            }
        });
        
        return filteredOreGroups;
    }

    public Response getMiningStrategies(List<String> availableGroups, Map<Integer, Double> requestedMinerals, double miningYieldPerCycle, double oreReprocessingEfficiency, double gasReprocessingEfficiency) {
        List<Ore> oreList = this.filterOreList(availableGroups, oreReprocessingEfficiency, gasReprocessingEfficiency);

        MiningOptimizer optimizer = new MiningOptimizer();

        return optimizer.calculateMiningStrategies(requestedMinerals, oreList, miningYieldPerCycle);
    }

    // public MiningOptimizer.OptimizationResult getMiningStrategies(List<String> availableGroups, Map<Integer, Double> requestedMinerals, double miningYieldPerCycle) {
    //     double reprocessingEfficiency = 1.0;
        
    //     List<Ore> oreGroups = this.filteredOreGroups(availableGroups);
    //     MiningOptimizer optimizer = new MiningOptimizer();
    
    //     MiningOptimizer.OptimizationResult result = optimizer.calculateAllPlans(requestedMinerals, oreGroups, miningYieldPerCycle);

    //     if (!result.getUnfulfillableMinerals().isEmpty()) {
    //         System.out.println("Cannot fulfill these minerals: " + result.getUnfulfillableMinerals());
    //     }

    //     System.out.println("Top mining plans:");
    //     // result.getTopPlans().forEach(System.out::println);

    //     return result;
    // }
}
