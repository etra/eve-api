package com.ether.sde.model;
/*
 * yaml example:
 * 
 2:
  descriptionID:
    de: Blaupausen sind Datengegenstände, die in der Industrie bei Fertigungs-, Forschungs-
      und Erfindungsaufträgen verwendet werden.
    en: Blueprints are data items used in industry for manufacturing, research and
      invention jobs
    es: Los planos son elementos de datos usados en la industria para los trabajos
      de fabricación, investigación e invención.
    fr: Les plans de construction sont des objets utilisés dans l'industrie pour réaliser
      des travaux de production, de recherche et d'invention
    ja: ブループリントは製造業、研究・発明アクティビティで使用するデータアイテムです
    ko: 데이터베이스의 일종으로 제조, 연구, 그리고 인벤션에 사용됩니다.
    ru: Чертежи используются в промышленности для производства предметов, во время
      исследовательских работ и в различных научно-исследовательских проектах.
    zh: 蓝图是一种工业物品，可用于制造、研究和发明项目
  hasTypes: false
  iconID: 2703
  nameID:
    de: Blaupausen & Reaktionen
    en: Blueprints & Reactions
    es: Planos y procesos reactivos
    fr: Plans de construction & Réactions
    ja: ブループリント
    ko: 블루프린트 및 반응식
    ru: Чертежи и реакции
    zh: 蓝图和反应
 */

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketGroupEntity implements HasEntityId {
    private String entityId;
    private Map<String, String> descriptionID;
    private boolean hasTypes;
    private int iconID;
    private Map<String, String> nameID;

    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Map<String, String> getDescriptionID() {
        return descriptionID;
    }
    public void setDescriptionID(Map<String, String> descriptionID) {
        this.descriptionID = descriptionID;
    }
    public boolean isHasTypes() {
        return hasTypes;
    }
    public void setHasTypes(boolean hasTypes) {
        this.hasTypes = hasTypes;
    }
    public int getIconID() {
        return iconID;
    }
    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
    public Map<String, String> getNameID() {
        return nameID;
    }
    public void setNameID(Map<String, String> nameID) {
        this.nameID = nameID;
    }

    
}
