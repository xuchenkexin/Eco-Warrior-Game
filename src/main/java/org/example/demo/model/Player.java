package org.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents the player character in the game
 */
public class Player extends Character {
    private List<Item> inventory;
    private int level;
    private int maxInventorySize;
    
    public Player(String name) {
        super(name, 100, 0); // Initial health 100, eco points 0
        this.inventory = new ArrayList<>();
        this.level = 1;
        this.maxInventorySize = 10;
    }
    
    @Override
    public void move(int x, int y) {
        position.move(x, y);
    }
    
    @Override
    public void interact(GameObject object) {
        if (object instanceof Item) {
            collectItem((Item) object);
        } else if (object instanceof RecycleBin) {
            // If interaction object is a recycle bin, show available waste items selection interface
            System.out.println("Interacting with recycle bin: " + ((RecycleBin) object).getName());
        }
    }
    
    public boolean collectItem(Item item) {
        if (inventory.size() < maxInventorySize) {
            inventory.add(item);
            ecoPoints += item.getEcoValue();
            return true;
        }
        return false;
    }
    
    /**
     * Place an item in a recycle bin
     * @param bin The recycle bin
     * @param item The item to place
     * @return Whether the item was successfully placed
     */
    public boolean placeItemInBin(RecycleBin bin, Item item) {
        if (inventory.contains(item)) {
            boolean correctlyProcessed = bin.processItem(this, item);
            if (correctlyProcessed) {
                inventory.remove(item);
                return true;
            } else {
                // If incorrectly sorted, item remains in inventory
                return false;
            }
        }
        return false;
    }
    
    /**
     * Drop an item
     * @param item The item to drop
     * @return Whether the item was successfully dropped
     */
    public boolean dropItem(Item item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
            // Dropping items deducts eco points
            ecoPoints -= item.getEcoValue() / 2;
            return true;
        }
        return false;
    }
    
    public void solveChallenge(Challenge challenge) {
        if (challenge.attemptSolve(this)) {
            ecoPoints += challenge.getRewardPoints();
        }
    }
    
    public boolean plantTree(Position position) {
        // Check if player has tree seed item
        Item seed = findItem(item -> item.getItemType() == ItemType.SEED);
        if (seed != null) {
            inventory.remove(seed);
            ecoPoints += 10;
            return true;
        }
        return false;
    }
    
    public boolean recycleWaste(Item waste) {
        if (waste.getItemType() == ItemType.WASTE) {
            if (inventory.contains(waste)) {
                inventory.remove(waste);
                ecoPoints += 5;
                return true;
            }
        }
        return false;
    }
    
    // Use functional programming to find items
    public Item findItem(Predicate<Item> condition) {
        return inventory.stream()
                .filter(condition)
                .findFirst()
                .orElse(null);
    }
    
    // Use functional programming to get list of specific item types
    public List<Item> getItemsByType(ItemType type) {
        return inventory.stream()
                .filter(item -> item.getItemType() == type)
                .collect(Collectors.toList());
    }
    
    public void levelUp() {
        level++;
        maxInventorySize += 2;
    }
    
    // Getter and Setter methods
    public List<Item> getInventory() {
        return inventory;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getMaxInventorySize() {
        return maxInventorySize;
    }
} 