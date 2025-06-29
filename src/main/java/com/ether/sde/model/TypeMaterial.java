package com.ether.sde.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeMaterial implements HasEntityId {
    private String entityId;
    private List<Material> materials;


    public static class Material {
        public int quantity;
        public int materialTypeID;
    }

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
