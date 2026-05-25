package org.example.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DragonAltar extends GameObject {

    private Set<String> requiredRelics;
    private List<Item> placedRelics;
    private boolean isDragonSummoned;

    public DragonAltar(int x, int y) {
        super("dragonAltar", x, y);
        this.interactive = true;
        this.isDragonSummoned = false;
        this.placedRelics = new ArrayList<>();
        this.requiredRelics = new HashSet<>();
        // These names should match the relic items from other levels
        requiredRelics.add("Coconut Tree Life Relic");
        requiredRelics.add("Clear Water Relic");
        requiredRelics.add("Tree of Life Relic");
        requiredRelics.add("Sacred Mountain Relic"); // The 4th relic, presumably found in this level
    }

    public boolean placeRelic(Item item) {
        if (item.getItemType() == ItemType.RELIC && requiredRelics.contains(item.getName()) && !isRelicPlaced(item)) {
            placedRelics.add(item);
            System.out.println(item.getName() + " has been placed on the altar.");
            checkForSummoning();
            return true;
        }
        System.out.println("This cannot be placed on the altar.");
        return false;
    }

    private boolean isRelicPlaced(Item item) {
        return placedRelics.stream().anyMatch(placed -> placed.getName().equals(item.getName()));
    }

    private void checkForSummoning() {
        if (placedRelics.size() == requiredRelics.size()) {
            summonDragon();
        }
    }

    private void summonDragon() {
        isDragonSummoned = true;
        System.out.println("All relics have been placed! The Sky Dragon has been summoned! The world is saved!");
        // In a real game, this would trigger a cutscene or the end of the game.
    }

    public boolean isDragonSummoned() {
        return isDragonSummoned;
    }

    @Override
    public void render() {
        // Rendering logic in GameRenderer
    }

    @Override
    public void update() {
        // No update logic needed
    }
} 