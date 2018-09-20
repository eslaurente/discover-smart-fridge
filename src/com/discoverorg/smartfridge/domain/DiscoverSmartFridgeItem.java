package com.discoverorg.smartfridge.domain;

import java.util.UUID;

public class DiscoverSmartFridgeItem {
    private final ItemType itemType;
    private final UUID itemUUID;

    private Double weightInPounds;

    public DiscoverSmartFridgeItem(ItemType itemType, Double weightInPounds) {
        itemUUID = UUID.randomUUID();
        this.itemType = itemType;
        this.weightInPounds = weightInPounds;
    }

    public String getName() {
        return itemType.name();
    }

    public long getItemTypeId() {
        return itemType.getTypeId();
    }

    public UUID getItemUUID() {
        return itemUUID;
    }

    public Double getWeightInPounds() {
        return weightInPounds;
    }

    public void setWeightInPounds(Double weightInPounds) {
        this.weightInPounds = weightInPounds;
    }
}
