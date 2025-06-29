package com.ether.sde.model;
/*
 * yaml example:
 
 2048:
  description:
    de: "Verwendet eine Kombination aus Dämpfungsfeldemittern und Redundanzystemen
      um die Auswirkungen von kritischen Systemschäden zu verhindern. \n\n\n\nVerleiht
      einen Bonus auf die Widerstandsfähigkeit von Schild, Panzerung und Schiffsstruktur.\n\n\n\nEs
      kann immer nur ein Schadenskontroll-System zu einem festgelegten Zeitpunkt eingebaut
      werden."
    en: "Utilizes a combination of containment field emitters and redundancy systems
      to mitigate the impact of critical system damage. \r\n\r\nGrants a bonus to
      resistance for shield, armor and hull.\r\n\r\nOnly one Damage Control can be
      fit at a given time."
    es: "Usa una combinación de emisores de campo de contención y sistemas redundantes
      para mitigar el impacto de daños críticos al sistema. \n\n\n\nOtorga una bonificación
      de resistencia de escudo, blindaje y casco.\n\n\n\nSolo se puede equipar un
      control de daños a la vez."
    fr: "Ce module combine des émetteurs de champ de confinement et des systèmes de
      redondance voués à mitiger l'impact dévastateur de dégâts critiques sur le système.
      \n\n\n\nOffre un bonus de résistance aux boucliers, au blindage et à la coque.\n\n\n\nUn
      seul régulateur de dégâts peut être équipé à la fois."
    ja: 格納型電界エミッターと冗長システムを組み合わせることにより、システムが深刻なダメージを受けた場合の影響を軽減する。シールド、アーマー、船体レジスタンスが向上する。ダメージコントロールは一度に一つしか装備できない。
    ko: 집속 필드 생성기와 비상 시스템을 사용하여 시스템에 가해지는 피해를 감소합니다. <br><br>실드, 장갑, 그리고 선체 저항력이 증가합니다.
      <br><br>데미지 컨트롤 모듈은 한 개만 장착할 수 있습니다.
    ru: "Использует технологию локальных защитных полей и принципы многократного резервирования
      для уменьшения последствий серьезных повреждений бортовых систем. \n\n\n\nПовышает
      сопротивляемость силовых полей, брони и корпуса корабля всем видам поражающего
      действия.\n\n\n\nДопускается одновременное использование лишь одного модуля
      боевой живучести (Damage Control)."
    zh: "将围场发射器和冗余系统结合使用，减轻由严重系统性损坏造成的影响。 \n\n\n\n提高护盾、装甲和结构的抗性。\n\n\n\n只能同时装配一个损伤控制。"
  groupID: 60
  iconID: 77
  marketGroupID: 615
  mass: 5000.0
  metaGroupID: 2
  name:
    de: Damage Control II
    en: Damage Control II
    es: Control de daños II
    fr: Contrôle des dégâts II
    ja: ダメージ制御II
    ko: 데미지 컨트롤 II
    ru: Damage Control II
    zh: 损伤控制 II
  portionSize: 1
  published: true
  variationParentTypeID: 2046
  volume: 5.0
 */
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeEntity implements HasEntityId {

    private String entityId;
    private int groupID;
    private Double mass;
    private Map<String, String> name;
    private Integer portionSize;
    private boolean published;
    private Integer marketGroupID;
    private double volume;


    private GroupEntity group; // <-- will be linked post-load

    @Override
    public String getEntityId() { return entityId; }
    
    @Override
    public void setEntityId(String entityId) { this.entityId = entityId; }

    // Getters and Setters
    public int getGroupID() { return groupID; }
    public void setGroupID(int groupID) { this.groupID = groupID; }

    public Double getMass() { return mass; }
    public void setMass(Double mass) { this.mass = mass; }

    public Map<String, String> getName() { return name; }
    public void setName(Map<String, String> name) { this.name = name; }

    public Integer getPortionSize() { return portionSize; }
    public void setPortionSize(Integer portionSize) { this.portionSize = portionSize; }

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public GroupEntity getGroup() { return group; }
    public void setGroup(GroupEntity group) { this.group = group; }

    public Integer getMarketGroupID() { return marketGroupID; }
    public void setMarketGroupID(Integer marketGroupID) { this.marketGroupID = marketGroupID; }

    public String getDisplayName() {
        return this.name.get("en");
    }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }

    public double getReprocessingVolume() { return this.volume * 100; }
    
}