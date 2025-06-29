package com.ether.api.model;
/**
  -d '{
    "requiredMaterials": [
      { "typeID": 34, "quantity": 100000 },
      { "typeID": 35, "quantity": 50000 },
      { "typeID": 36, "quantity": 20000 }
    ]
  }'
 */
import java.util.List;
public class MiningStrategyRequest {
    private List<Material> requiredMaterials;

    public static class Material {
        private int typeID;
        private double quantity;

        public Material() {}

        public Material(int typeID, double quantity) {
            this.typeID = typeID;
            this.quantity = quantity;
        }

        public int getTypeID() {
            return typeID;
        }

        public void setTypeID(int typeID) {
            this.typeID = typeID;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }
    }

    public MiningStrategyRequest() {}

    public MiningStrategyRequest(List<Material> requiredMaterials) {
        this.requiredMaterials = requiredMaterials;
    }

    public List<Material> getRequiredMaterials() {
        return requiredMaterials;
    }

    public void setRequiredMaterials(List<Material> requiredMaterials) {
        this.requiredMaterials = requiredMaterials;
    }
}