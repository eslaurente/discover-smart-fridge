package com.discoverorg.smartfridge.domain;

import java.util.*;

public class DiscoverSmartFridgeContainer {
    private static final Double MAX_WEIGHT_IN_POUNDS = 50d;
    private final String name;
    private final Map<UUID, DiscoverSmartFridgeItem> items = new HashMap<>();
    private Double weightInPounds = 0d;

    public DiscoverSmartFridgeContainer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<DiscoverSmartFridgeItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public void addItem(DiscoverSmartFridgeItem item) {
        items.put(item.getItemUUID(), item);
        weightInPounds += item.getWeightInPounds();
    }

    public void removeItem(UUID itemUUID) {
        DiscoverSmartFridgeItem item = items.remove(itemUUID);
        if (item != null) {
            weightInPounds -= item.getWeightInPounds();
        }
    }

    public boolean exists(UUID itemUUID) {
        return items.containsKey(itemUUID);
    }

    public boolean fits(DiscoverSmartFridgeItem item) {
        return (item.getWeightInPounds() + weightInPounds) <= MAX_WEIGHT_IN_POUNDS;
    }

    public boolean full() {
        return weightInPounds.equals(MAX_WEIGHT_IN_POUNDS);
    }

    public boolean empty() {
        return weightInPounds == 0d;
    }
}
