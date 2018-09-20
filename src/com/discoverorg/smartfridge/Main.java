package com.discoverorg.smartfridge;

import com.discoverorg.smartfridge.domain.DiscoverSmartFridgeItem;
import com.discoverorg.smartfridge.domain.DiscoverSmartFridgeManager;
import com.discoverorg.smartfridge.domain.ItemType;
import com.discoverorg.smartfridge.exception.FridgeFullException;
import com.discoverorg.smartfridge.exception.ItemNotFoundException;
import com.discoverorg.smartfridge.exception.NullItemException;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        DiscoverSmartFridgeItem apple = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem apple1 = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem apple2 = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem apple3 = new DiscoverSmartFridgeItem(ItemType.APPLE, 3d);
        DiscoverSmartFridgeItem orange = new DiscoverSmartFridgeItem(ItemType.ORANGE, 2d);
        DiscoverSmartFridgeItem orange1 = new DiscoverSmartFridgeItem(ItemType.ORANGE, 5d);
        DiscoverSmartFridgeItem potato = new DiscoverSmartFridgeItem(ItemType.POTATO, 20d);
        DiscoverSmartFridgeItem milk = new DiscoverSmartFridgeItem(ItemType.MILK, 3d);

        DiscoverSmartFridgeManager fridgeManager = new DiscoverSmartFridgeManager()
                // Set up fridge manager item types and respective max weights
                .addMaxFill(ItemType.APPLE, 13d)
                .addMaxFill(ItemType.ORANGE, 3d)
                .addMaxFill(ItemType.POTATO, 40d)
                .addMaxFill(ItemType.MILK, 10d)
                // Add some items in the fridge
                .addItem(apple)
                .addItem(apple1)
                .addItem(apple2)
                .addItem(apple3)
                .addItem(orange)
                .addItem(orange1)
                .addItem(potato)
                .addItem(milk);

        fridgeManager.printItems(1d);

        // Remove specific apple by UUID
        fridgeManager.removeItem(apple.getItemUUID());

        fridgeManager.printItems(1d);

        // Forgetting Apple item type
        fridgeManager.forgetItem(ItemType.APPLE.getTypeId());

        fridgeManager.printItems(1d);

        System.out.println("\n\n*** EDGE CASES ***\n");
        // Catch NullItemException
        try {

            fridgeManager.addItem(null);
        } catch (NullItemException e) {
            System.out.println("[ ERROR: ADD ITEM FAILED ]: Cannot add a null item");
        }

        // Catch ItemNotFoundException
        UUID uuid = UUID.randomUUID();
        try {

            fridgeManager.removeItem(uuid);
        } catch (ItemNotFoundException e) {
            System.out.println("[ ERROR: REMOVE FAILED ]: UUID " + uuid + " not found");
        }

        // Catch FridgeFullException
        // Note: there are only 4 containers in DiscoverSmartFridgeManager, and each only can carry 50 pounds
        try {
            fridgeManager.addItem(new DiscoverSmartFridgeItem(ItemType.POTATO, 50d))
                    .addItem(new DiscoverSmartFridgeItem(ItemType.POTATO, 50d))
                    .addItem(new DiscoverSmartFridgeItem(ItemType.POTATO, 50d))
                    .addItem(new DiscoverSmartFridgeItem(ItemType.POTATO, 50d));
        } catch (FridgeFullException e) {
            System.out.println("[ ERROR: ADD FAILED ]: Cannot add any more items as the fridge is full. Please remove items and try again");
        }
    }
}
