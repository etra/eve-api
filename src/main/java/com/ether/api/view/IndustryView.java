package com.ether.api.view;

import java.util.List;

import com.ether.sde.model.Blueprint.Material;
import com.ether.sde.model.TypeEntity;

public class IndustryView {

    private final String id;
    private final String name;
    private final Integer runs;
    private final Integer me;
    
    private final List<Material> materials;

    public IndustryView(TypeEntity item, List<Material> materials, Integer runs, Integer me) {
        this.id = item.getEntityId();
        this.name = item.getDisplayName();
        this.runs = runs;
        this.me = me;
        this.materials = materials;
    }

    public IndustryView(TypeEntity item, List<Material> materials) {
        this.id = item.getEntityId();
        this.name = item.getDisplayName();
        this.runs = 1;
        this.me = 0;
        this.materials = materials;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRuns() {
        return runs;
    }

    public Integer getMe() {
        return me;
    }
    
    public List<Material> getMaterials() {
        return materials;
    }
}
