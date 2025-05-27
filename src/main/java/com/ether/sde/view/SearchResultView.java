package com.ether.sde.view;

public class SearchResultView {
    private final int typeId;
    private final String name;
    private final String marketGroupName;

    public SearchResultView(int typeId, String name, String marketGroupName) {
        this.typeId = typeId;
        this.name = name;
        this.marketGroupName = marketGroupName;
    }

    public int getTypeId() { return typeId; }
    public String getName() { return name; }
    public String getMarketGroupName() { return marketGroupName; }
}
