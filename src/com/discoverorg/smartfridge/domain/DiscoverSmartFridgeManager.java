package com.discoverorg.smartfridge.domain;

import com.discoverorg.smartfridge.exception.FridgeFullException;
import com.discoverorg.smartfridge.exception.ItemNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DiscoverSmartFridgeManager implements SmartFridgeManager {
    private List<DiscoverSmartFridgeContainer> containers = Arrays.asList(
            new DiscoverSmartFridgeContainer("CONTAINER_1"),
            new DiscoverSmartFridgeContainer("CONTAINER_2"),
            new DiscoverSmartFridgeContainer("CONTAINER_3"),
            new DiscoverSmartFridgeContainer("CONTAINER_4")
    );
    private HashMap<Long, Double> fillTargets = new HashMap<>();

    public DiscoverSmartFridgeManager() {
    }

    public void addMaxFill(ItemType itemType, Double maxWeightInPounds) {
        fillTargets.put(itemType.getTypeId(), maxWeightInPounds);
    }

    public void addItem(DiscoverSmartFridgeItem fridgeItem) throws FridgeFullException {
        if (fridgeItem == null) {
            // TODO: could wrap in Optional
            return;
        }

        UUID itemUUID = fridgeItem.getItemUUID();
        long itemType = fridgeItem.getItemTypeId();
        String name = fridgeItem.getName();
        DiscoverSmartFridgeContainer container = containers.stream()
                .filter(aContainer -> !aContainer.full() && aContainer.fits(fridgeItem))
                .findFirst()
                .orElseThrow(FridgeFullException::new);

        container.addItem(fridgeItem);
        Double fillFactor = getFillFactor(itemType);
        handleItemAdded(itemType, itemUUID.toString(), name, fillFactor);
    }

    public void removeItem(UUID itemUUID) throws ItemNotFoundException {
        DiscoverSmartFridgeContainer container = containers.stream()
                .filter(aContaier -> aContaier.exists(itemUUID))
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
        container.removeItem(itemUUID);
        handleItemRemoved(itemUUID.toString());
    }

    public boolean spaceAvailable() {
        return (containers.stream().filter(DiscoverSmartFridgeContainer::full).count()) < containers.size();
    }

    public List<DiscoverSmartFridgeContainer> getContainers() {
        return containers;
    }


    @Override
    public void handleItemRemoved(String itemUUID) {
        System.out.println("[ Event: ITEM REMOVED ]\n\titemUUID: " + itemUUID);
    }

    @Override
    public void handleItemAdded(long itemType, String itemUUID, String name, Double fillFactor) {
        String output = "[ Event: ITEM ADDED ]\n\t" +
                "name: " + name + ", itemUUID: " +
                itemUUID + ", itemType: " + itemType + ", fillFactor: " + fillFactor;
        System.out.println(output);
    }

    @Override
    public Object[] getItems(Double fillFactor) {
        return containers.stream()
                .flatMap(container -> container.getItems().stream())
                .filter(item -> fillTargets.containsKey(item.getItemTypeId()))
                .collect(Collectors.groupingBy(DiscoverSmartFridgeItem::getItemTypeId))
                .keySet().stream()
                .filter(item -> getFillFactor(item) <= fillFactor)
                .map(itemType -> new Object[]{itemType, getFillFactor(itemType)})
                .toArray();
    }

    @Override
    public Double getFillFactor(long itemType) {
        return (containers.stream()
                .flatMap(container -> container.getItems().stream())
                .filter(item -> item.getItemTypeId() == itemType)
                .mapToDouble(DiscoverSmartFridgeItem::getWeightInPounds)
                .sum()
        ) / fillTargets.get(itemType);
    }

    @Override
    public void forgetItem(long itemType) {
        fillTargets.remove(itemType);
    }
}
