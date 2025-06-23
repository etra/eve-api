package com.ether.api.view;

public class IndustryItemView {

    private final String id;
    private final String name;
    private final String activityType;

    public IndustryItemView(String id, String name, String activityType) {
        this.id = id;
        this.name = name;
        this.activityType = activityType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getActivityType() {
        return activityType;
    }
}
