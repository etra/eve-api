package com.ether.sde.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Blueprint {

    public int blueprintTypeID;
    public int maxProductionLimit;
    public Map<String, Activity> activities; // e.g., "manufacturing", "copying", etc.

    public static class Activity {
        public List<Material> materials;
        public List<Skill> skills;
        public List<Product> products;
        public Integer time;

        public Product getProduct(){
            if (this.products != null && !this.products.isEmpty()) {
                return this.products.get(0);
            }
            return null;
        }

        public List<Material> getMaterials(){
            if (this.materials != null && !this.materials.isEmpty()) {
                return this.materials;
            }
            return new ArrayList<>();
        }
    }

    public static class Material {
        public int quantity;
        public int typeID;

        static Material create(int typeID, int quantity) {
            Material m = new Material();
            m.quantity = quantity;
            m.typeID = typeID;
            return m;
        }
    }

    public static class Skill {
        public int level;
        public int typeID;
    }

    public static class Product {
        public int quantity;
        public int typeID;
        public Double probability; // Only invention has this field
    }

    public List<Material> getManufacturingMaterials() {
        if (this.activities.get("manufacturing") != null) {
            return this.activities.get("manufacturing").getMaterials();
        }
        return new ArrayList<>();
    }

    public List<Material> getManufacturingMaterials(int desiredQuantity, int meLevel, double materialBonus) {
        //NumberOfRuns = ceil(DesiredItemCount / OutputPerRun)
        //PerRunQty = max(1, floor(BaseQuantity × (1 - 0.01 × ME) × (1 - StructureMaterialBonus)))

        //FinalQuantity = PerRunQty × NumberOfRuns


        if (this.getManufacturingProduct() == null) {
            return new ArrayList<>();
        }
        List<Material> allMaterials = new ArrayList<>();
        List<Material> result = new ArrayList<>();
        if (this.getManufacturingMaterials() != null) {
            allMaterials.addAll(this.getManufacturingMaterials());
        }
        
        int quantityPerRun = this.getManufacturingProduct().quantity;
        int numberOfRuns = (int) Math.ceil((double) desiredQuantity / quantityPerRun);
        

        double reductionFactor = 1 - 0.01 * meLevel; // ME reduces materials by 2% per level
        for (Material mat : allMaterials) {
            double perRunQty = Math.max(1,  mat.quantity * reductionFactor * materialBonus);
            int finalQuantity = (int) Math.floor(perRunQty * numberOfRuns);
            result.add(Material.create(mat.typeID, finalQuantity));
        }
        return result;
    }


    public List<Material> getReactionMaterials() {
        if (this.activities.get("reaction") != null) {
            return this.activities.get("reaction").getMaterials();
        }
        return new ArrayList<>();
    }

    public String getActivityType() {
        if (this.activities.get("manufacturing") != null) {
            return "manufacturing";
        }
        if (this.activities.get("reaction") != null) {
            return "reaction";
        }

        return "raw";
    }

    
    public List<Material> getReactionMaterials(int desiredQuantity, double materialBonus) {
        if (this.getReactionProduct() == null) {
            return new ArrayList<>();
        }
        List<Material> baseMaterials = this.getReactionMaterials();
        List<Material> result = new ArrayList<>();
        
        int quantityPerRun = this.getReactionProduct().quantity;
        int requiredRuns = (int) Math.ceil((double) desiredQuantity / quantityPerRun);
        
        for (Material m : baseMaterials) {
            double perRunQty = Math.max(1, m.quantity * materialBonus);
            int total = (int) Math.floor(perRunQty * requiredRuns);
            result.add(Material.create(m.typeID, total));
        }
    
        return result;
    }

    public List<Material> getMaterials() {
        List<Material> requiredMaterials = new ArrayList<>();
        requiredMaterials.addAll(this.getManufacturingMaterials());
        requiredMaterials.addAll(this.getReactionMaterials());
        return requiredMaterials;
    }


    public List<Material> getMaterials(int runs, int meLevel, double materialBonus) {
        List<Material> requiredMaterials = new ArrayList<>();
        requiredMaterials.addAll(this.getManufacturingMaterials(runs, meLevel, materialBonus));
        requiredMaterials.addAll(this.getReactionMaterials(runs, materialBonus));
        return requiredMaterials;
    }

    public Product getManufacturingProduct() {
        if (this.activities.get("manufacturing") != null) {
            return this.activities.get("manufacturing").getProduct();
        }
        return null;
    }

    public Product getReactionProduct() {
        if (this.activities.get("reaction") != null) {
            return this.activities.get("reaction").getProduct();
        }
        return null;
    }
    
}