package com.ether.sde.model;
/*
 * yaml example:
 18:
  anchorable: false
  anchored: false
  categoryID: 4
  fittableNonSingleton: false
  iconID: 22
  name:
    de: Mineralien
    en: Mineral
    es: Mineral
    fr: Minéral
    ja: 無機物
    ko: 광물
    ru: Минералы
    zh: 矿物
  published: true
  useBasePrice: true
 */
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    private boolean anchorable;
    private boolean anchored;
    private int categoryID;
    private boolean fittableNonSingleton;
    private Map<String, String> name;
    private boolean published;
    private boolean useBasePrice;

    // Getters and Setters
    public boolean isAnchorable() { return anchorable; }
    public void setAnchorable(boolean anchorable) { this.anchorable = anchorable; }

    public boolean isAnchored() { return anchored; }
    public void setAnchored(boolean anchored) { this.anchored = anchored; }

    public int getCategoryID() { return categoryID; }
    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }

    public boolean isFittableNonSingleton() { return fittableNonSingleton; }
    public void setFittableNonSingleton(boolean fittableNonSingleton) { this.fittableNonSingleton = fittableNonSingleton; }

    public Map<String, String> getName() { return name; }
    public void setName(Map<String, String> name) { this.name = name; }

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public boolean isUseBasePrice() { return useBasePrice; }
    public void setUseBasePrice(boolean useBasePrice) { this.useBasePrice = useBasePrice; }
}
