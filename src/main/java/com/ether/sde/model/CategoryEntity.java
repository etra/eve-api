package com.ether.sde.model;
/*
 * yaml example:
 *   iconID: 33
  name:
    de: Zubehör
    en: Accessories
    es: Accesorios
    fr: Accessoires
    ja: アクセサリー
    ko: 악세서리
    ru: Вспомогательные предметы
    zh: 附件
  published: true
 */


import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryEntity implements HasEntityId {
    private String entityId;

    private int iconID;
    private Map<String, String> name;
    private boolean published;

    @Override
    public String getEntityId() { return entityId; }

    @Override
    public void setEntityId(String entityId) { this.entityId = entityId; }

    // Getters and Setters
    public int getIconID() {
        return iconID;
    }
    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
    public Map<String, String> getName() {
        return name;
    }
    public void setName(Map<String, String> name) {
        this.name = name;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean published) {
        this.published = published;
    }


}
