package org.example.demo.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents recycle bins in the game, used for waste sorting
 */
public class RecycleBin extends GameObject {
    private String name;
    private Set<ItemType> acceptedTypes; // Types of waste accepted by this bin
    private Color binColor; // Bin color
    
    public RecycleBin(String name, ItemType acceptedType, int x, int y, String imagePath) {
        this(name, new HashSet<>(Arrays.asList(acceptedType)), x, y, imagePath);
    }
    
    public RecycleBin(String name, Set<ItemType> acceptedTypes, int x, int y, String imagePath) {
        super("recycleBin", x, y);
        this.name = name;
        this.acceptedTypes = acceptedTypes;
        this.interactive = true;
        
        // Set bin color based on accepted waste type
        setBinColor();
        
        // Try to load the corresponding bin image
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                if (getClass().getResource(imagePath) != null) {
                    this.sprite = new Image(getClass().getResourceAsStream(imagePath));
                } else {
                    System.err.println("Recycle bin image not found at path: " + imagePath);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load recycle bin image: " + e.getMessage());
        }
    }
    
    /**
     * Set bin color based on type
     */
    private void setBinColor() {
        if (acceptedTypes.size() == 1) {
            ItemType type = acceptedTypes.iterator().next();
            switch (type) {
                case PLASTIC:
                    binColor = Color.BLUE;
                    break;
                case METAL:
                    binColor = Color.YELLOW;
                    break;
                case ORGANIC:
                    binColor = Color.GREEN;
                    break;
                default:
                    binColor = Color.GRAY;
            }
        } else {
            binColor = Color.GRAY; // Default color for multi-type bins
        }
    }
    
    /**
     * Check if an item can be placed in this bin
     * @param item Item to check
     * @return Whether the item can be accepted
     */
    public boolean canAccept(Item item) {
        return acceptedTypes.contains(item.getItemType());
    }
    
    /**
     * Process player placing an item
     * @param player The player
     * @param item Item being placed
     * @return Whether the item was correctly placed
     */
    public boolean processItem(Player player, Item item) {
        if (canAccept(item)) {
            // Correctly sorted waste, reward eco points
            player.setEcoPoints(player.getEcoPoints() + 5);
            System.out.println("Waste correctly sorted! Earned 5 additional eco points!");
            return true;
        } else {
            // Incorrectly sorted waste, deduct eco points
            player.setEcoPoints(player.getEcoPoints() - 2);
            System.out.println("Waste incorrectly sorted! Lost 2 eco points!");
            return false;
        }
    }
    
    @Override
    public void render() {
        // Rendering logic implemented in GameRenderer
    }
    
    @Override
    public void update() {
        // Recycle bins typically don't need update logic
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public Set<ItemType> getAcceptedTypes() {
        return acceptedTypes;
    }
    
    public Color getBinColor() {
        return binColor;
    }
} 