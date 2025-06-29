package com.ether.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ether.api.model.ApiResponse;
import com.ether.sde.model.mining.Ore;
import com.ether.sde.model.mining.optimization.Response;
import com.ether.sde.service.MiningService;


/**
curl -X POST "http://localhost:8080/api/v1/mining/strategies?reprocessing=5&reprocessingEfficiency=5&commonMoonOreProcessing=4&uncommonMoonOreProcessing=3&structureBonus=0.02&maxOptions=10" \
  -H "Content-Type: application/json" \
  -d '{
    "requiredMaterials": [
      { "typeID": 34, "quantity": 100000 },   // Tritanium
      { "typeID": 35, "quantity": 50000 },    // Pyerite
      { "typeID": 36, "quantity": 20000 }     // Mexallon
    ]
  }'
 */
@RestController
@RequestMapping("/mining")
public class MiningController {

    private final MiningService miningService;

    public MiningController(MiningService miningService) {
        this.miningService = miningService;
    }
    // @GetMapping("/ores")
    // public ResponseEntity<ApiResponse<List<OreGroupView>>> getOres() {
    //   List<String> availableGroups = List.of("458");
    //   // List<String> availableGroups = new ArrayList<>();

    //   List<Ore> oreGroups = this.miningService.getOreGroups(availableGroups);
    //   // List<String> availableGroups = List.of("460");

    //   List<OreGroupView> oreGroupViews = oreGroups.stream().map(OreGroupView::new).collect(Collectors.toList());

    //   return ResponseEntity.ok(ApiResponse.success(oreGroupViews));
    // }

    @GetMapping("/ores-all")
    public ResponseEntity<ApiResponse<List<Ore>>> getOresAll() {
      List<String> availableGroups = List.of("462");
      double oreReprocessingEfficiency = 0.96; 
      double gasReprocessingEfficiency = 0.96;
      List<Ore> oreGroups = this.miningService.getOreList(availableGroups, oreReprocessingEfficiency, gasReprocessingEfficiency);
      // List<String> availableGroups = List.of("460");

      return ResponseEntity.ok(ApiResponse.success(oreGroups));
    }

    // @PostMapping("/strategies")
    // public ResponseEntity<ApiResponse<String>> getMiningStrategies(@RequestBody MiningStrategyRequest request, @RequestParam(required = false) Integer reprocessing, @RequestParam(required = false) Integer reprocessingEfficiency, @RequestParam(required = false) Integer commonMoonOreProcessing, @RequestParam(required = false) Integer uncommonMoonOreProcessing, @RequestParam(required = false) Double structureBonus, @RequestParam(required = false) Integer maxOptions) {
    //     System.out.println(request);
    //     return ResponseEntity.ok(ApiResponse.success("test")));
    // }

    @GetMapping("/test-single")
    public ResponseEntity<ApiResponse<Response>> getOreMineralsSingle() {
      double oreReprocessingEfficiency = 0.96;
      double gasReprocessingEfficiency = 0.96;

      List<String> availableGroups = List.of("462");
      // List<String> availableGroups = new ArrayList<>();
      
      double miningYieldPerCycle = 60.0;
      
      Map<Integer, Double> requestedMinerals = new HashMap<>();
      requestedMinerals.put(34, 1000.0);
      // requestedMinerals.put(35, 100.0);
      
      
      Response result = this.miningService.getMiningStrategies(availableGroups, requestedMinerals, miningYieldPerCycle, oreReprocessingEfficiency, gasReprocessingEfficiency);
      
      return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/test-multiple")
    public ResponseEntity<ApiResponse<Response>> getOreMinerals() {
      List<String> availableGroups = List.of("462", "458", "460");
      // List<String> availableGroups = new ArrayList<>();
      double oreReprocessingEfficiency = 0.96;
      double gasReprocessingEfficiency = 0.96;
      double miningYieldPerCycle = 60.0;
      
      Map<Integer, Double> requestedMinerals = new HashMap<>();
      requestedMinerals.put(36, 100.0);
      requestedMinerals.put(35, 100.0);
      requestedMinerals.put(34, 1000.0);
      
      
      Response result = this.miningService.getMiningStrategies(availableGroups, requestedMinerals, miningYieldPerCycle, oreReprocessingEfficiency, gasReprocessingEfficiency);
      
      return ResponseEntity.ok(ApiResponse.success(result));
    }
}
