package org.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.demo.model.Player;

public class PottingBench extends GameObject {

    private List<Item> items;
    private static final int CAPACITY = 6;
    private boolean isEnlarged = false;

    public PottingBench(int x, int y) {
        super("pottingBench", x, y);
        this.items = new ArrayList<>();
        this.interactive = true;
    }

    public boolean addItem(Item item) {
        if (items.size() < CAPACITY) {
            items.add(item);
            System.out.println(item.getName() + " added to potting bench.");
            return true;
        }
        System.out.println("Potting bench is full.");
        return false;
    }

    public void water(Player player, Environment environment) {
        if (isReadyForWatering()) {
            System.out.println("Watering the seeds... They are growing!");
            items.clear();
            for (int i = 0; i < 3; i++) {
                Item grownSeed = new Item("Grown Seed", ItemType.GROWN_SEED, 10, getPosition().getX() + (i - 1) * 40, getPosition().getY() + 50);
                environment.addObject(grownSeed);
            }
            player.setEcoPoints(player.getEcoPoints() + 30);
            this.isEnlarged = true;
        } else {
            System.out.println("Not enough seeds or soil on the bench to grow anything.");
        }
    }
    
    public boolean isReadyForWatering() {
        long seedCount = items.stream().filter(item -> item.getItemType() == ItemType.SEED).count();
        long soilCount = items.stream().filter(item -> item.getItemType() == ItemType.SOIL).count();
        return seedCount >= 3 && soilCount >= 3;
    }

    public boolean isEnlarged() {
        return isEnlarged;
    }

    public List<Item> getItems() {
        return items;
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