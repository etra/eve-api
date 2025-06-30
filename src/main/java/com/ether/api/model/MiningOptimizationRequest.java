package com.ether.api.model;


import java.util.List;

public class MiningOptimizationRequest {

    private List<String> availableOres;
    private List<RequestedItem> requestedItems;

    public MiningOptimizationRequest() {
    }

    public MiningOptimizationRequest(
        List<String> availableOres,
        List<RequestedItem> requestedItems) {
        this.availableOres = availableOres;
        this.requestedItems = requestedItems;
    }


    public List<String> getAvailableOres() {
        return availableOres;
    }

    public void setAvailableOres(List<String> availableOres) {
        this.availableOres = availableOres;
    }

    public List<RequestedItem> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(List<RequestedItem> requestedItems) {
        this.requestedItems = requestedItems;
    }

    public static class RequestedItem {
        private String name;
        private int quantity;

        public RequestedItem() {
        }

        public RequestedItem(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}

