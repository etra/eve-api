package com.ether.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ether.api.model.ApiResponse;
import com.ether.api.model.MiningOptimizationRequest;
import com.ether.api.view.OptimizerResultView;
import com.ether.api.view.OreView;
import com.ether.sde.model.mining.Ore;
import com.ether.sde.model.mining.optimization.Response;
import com.ether.sde.service.ItemService;
import com.ether.sde.service.MiningService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;





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
    private final ItemService itemService;

    public MiningController(MiningService miningService, ItemService itemService) {
        this.miningService = miningService;
        this.itemService = itemService;
    }
    @GetMapping("/ores")
    public ResponseEntity<ApiResponse<List<OreView>>> getOres() {
      List<String> availableGroups = new ArrayList<>();

      List<Ore> oreGroups = this.miningService.getOreList(availableGroups, 1, 1);
      

      List<OreView> response = oreGroups.stream().map(OreView::new).collect(Collectors.toList());

      return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/ores-all")
    public ResponseEntity<ApiResponse<List<Ore>>> getOresAll() {
      List<String> availableGroups = List.of("462");
      double oreReprocessingEfficiency = 0.96; 
      double gasReprocessingEfficiency = 0.96;
      List<Ore> oreGroups = this.miningService.getOreList(availableGroups, oreReprocessingEfficiency, gasReprocessingEfficiency);
      // List<String> availableGroups = List.of("460");

      return ResponseEntity.ok(ApiResponse.success(oreGroups));
    }

    /**
     * /api/v1/mining/optimization?miningSpeed=60.0&gasEfficiency=0.9&oreEfficiency=0.9
     * 
     * @param request
     * @param miningSpeed
     * @param reprocessingEfficiency
     * @param commonMoonOreProcessing
     * @param uncommonMoonOreProcessing
     * @param structureBonus
     * @param maxOptions
     * @return
     */
    @PostMapping("/optimization")
    public ResponseEntity<ApiResponse<OptimizerResultView>> getOreMinerals(@RequestBody MiningOptimizationRequest request, @RequestParam(required = false, defaultValue = "60.0") Double miningSpeed, @RequestParam(required = false, defaultValue = "1") Double gasEfficiency, @RequestParam(required = false, defaultValue = "1") Double oreEfficiency) {
      
      List<String> availableOres = request.getAvailableOres();
      List<MiningOptimizationRequest.RequestedItem> requestedItems = request.getRequestedItems();
      // double miningSpeed = request.getMiningSpeed();
      // double gasEfficiency = request.getGasEfficiency();
      // double oreEfficiency = request.getOreEfficiency();
      System.out.println("--------------------------------");
      System.out.println("oreEfficiency: " + oreEfficiency);
      System.out.println("gasEfficiency: " + gasEfficiency);
      System.out.println("miningSpeed: " + miningSpeed);
      System.out.println("availableOres: " + availableOres);
      System.out.println("requestedItems: " + requestedItems);
      System.out.println("--------------------------------");
      
      Map<Integer, Double> requestedMinerals = new HashMap<>();
      for (MiningOptimizationRequest.RequestedItem item : requestedItems) {
        String itemKey = this.itemService.getItemKeyByName(item.getName());
        if (itemKey != null) {
          requestedMinerals.put(Integer.parseInt(itemKey), Double.valueOf(item.getQuantity()));
        }
      }

      Response result = this.miningService.getMiningStrategies(availableOres, requestedMinerals, miningSpeed, oreEfficiency, gasEfficiency);
      
      OptimizerResultView optimiserResultView = new OptimizerResultView(result.getUnfulfillableEntities(), result.getTopPlans());
      ObjectMapper mapper = new ObjectMapper();
      try {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(optimiserResultView);
        System.out.println(json);
        String json2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(json2);
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }


      return ResponseEntity.ok(ApiResponse.success(optimiserResultView));
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
      requestedMinerals.put(2046, 100.0);
      // requestedMinerals.put(35, 100.0);
      
      
      Response result = this.miningService.getMiningStrategies(availableGroups, requestedMinerals, miningYieldPerCycle, oreReprocessingEfficiency, gasReprocessingEfficiency);
      
      return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/test-multiple")
    public ResponseEntity<ApiResponse<Response>> getOreMinerals() {
      List<String> availableGroups = List.of("46688");
      // List<String> availableGroups = new ArrayList<>();
      double oreReprocessingEfficiency = 0.96;
      double gasReprocessingEfficiency = 0.96;
      double miningYieldPerCycle = 60.0;
      
      Map<Integer, Double> requestedMinerals = new HashMap<>();
      requestedMinerals.put(36, 100.0);
      requestedMinerals.put(35, 100.0);
      requestedMinerals.put(34, 1000.0);
      requestedMinerals.put(2046, 100.0);
      
      
      Response result = this.miningService.getMiningStrategies(availableGroups, requestedMinerals, miningYieldPerCycle, oreReprocessingEfficiency, gasReprocessingEfficiency);
      ObjectMapper mapper = new ObjectMapper();
      try {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println(json);
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }
      return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/test-multiple-2")
    public ResponseEntity<ApiResponse<OptimizerResultView>> getOreMinerals2() {
      // List<String> availableGroups = List.of("462", "458", "460");
      List<String> availableGroups = List.of("46688");
      double oreReprocessingEfficiency = 0.96;
      double gasReprocessingEfficiency = 0.96;
      double miningYieldPerCycle = 60.0;
      
      Map<Integer, Double> requestedMinerals = new HashMap<>();
      requestedMinerals.put(36, 100.0);
      requestedMinerals.put(35, 100.0);
      requestedMinerals.put(34, 1000.0);
      requestedMinerals.put(2046, 100.0);
      
      
      Response result = this.miningService.getMiningStrategies(availableGroups, requestedMinerals, miningYieldPerCycle, oreReprocessingEfficiency, gasReprocessingEfficiency);
      OptimizerResultView optimiserResultView = new OptimizerResultView(result.getUnfulfillableEntities(), result.getTopPlans());
      ObjectMapper mapper = new ObjectMapper();
      try {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(optimiserResultView);
        System.out.println(json);
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }
      return ResponseEntity.ok(ApiResponse.success(optimiserResultView));
    }
}
