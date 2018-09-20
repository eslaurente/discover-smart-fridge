package com.discoverorg.smartfridge;

import com.discoverorg.smartfridge.domain.DiscoverSmartFridgeItem;
import com.discoverorg.smartfridge.domain.DiscoverSmartFridgeManager;
import com.discoverorg.smartfridge.domain.ItemType;

public class Main {

    public static void main(String[] args) {
        DiscoverSmartFridgeManager fridgeManager = new DiscoverSmartFridgeManager();
        // Set up fridge manager item types and respective max weights
        fridgeManager.addMaxFill(ItemType.APPLE, 13d);
        fridgeManager.addMaxFill(ItemType.ORANGE, 3d);
        fridgeManager.addMaxFill(ItemType.POTATO, 40d);
        fridgeManager.addMaxFill(ItemType.MILK, 10d);

        // Add some items in the fridge
        DiscoverSmartFridgeItem apple = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem apple1 = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem apple2 = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem apple3 = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem orange = new DiscoverSmartFridgeItem(ItemType.ORANGE, 2d);
        DiscoverSmartFridgeItem orange1 = new DiscoverSmartFridgeItem(ItemType.ORANGE, 5d);
        DiscoverSmartFridgeItem potato = new DiscoverSmartFridgeItem(ItemType.POTATO, 20d);
        DiscoverSmartFridgeItem milk = new DiscoverSmartFridgeItem(ItemType.MILK, 3d);
        fridgeManager.addItem(apple);
        fridgeManager.addItem(apple1);
        fridgeManager.addItem(apple2);
        fridgeManager.addItem(apple3);
        fridgeManager.addItem(orange);
        fridgeManager.addItem(orange1);
        fridgeManager.addItem(potato);
        fridgeManager.addItem(milk);

        printItems(fridgeManager, 1d);

        fridgeManager.removeItem(apple.getItemUUID());

        // Forgetting Apple item type
        fridgeManager.forgetItem(ItemType.APPLE.getTypeId());

        printItems(fridgeManager, 1d);
    }

    public static void printItems(DiscoverSmartFridgeManager fridgeManager, Double fillFactorCriteria) {
        System.out.println("\n** Listing items with fill factor less than or equal to " + fillFactorCriteria + " **");
        for (Object items : fridgeManager.getItems(fillFactorCriteria)) {
            Long itemType = (Long)((Object[]) items)[0];
            Double fillFactor = (Double)((Object[]) items)[1];
            System.out.println("itemType: " + itemType + ", fillFactor: " + fillFactor);
        }
        System.out.println();
    }
}
