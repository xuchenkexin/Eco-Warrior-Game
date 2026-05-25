package org.example.demo.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents collectible items in the game
 */
public class Item extends GameObject {
    private String name;
    private ItemType type;
    private int ecoValue;
    
    public Item(String name, ItemType type, int ecoValue, int x, int y) {
        super("item", x, y);
        this.name = name;
        this.type = type;
        this.ecoValue = ecoValue;
        this.interactive = true;
        
        // Load different images based on item type, use default rendering if loading fails
        loadItemImage();
    }
    
    /**
     * Load item image
     */
    private void loadItemImage() {
        try {
            // Try to load image based on item name first, then by type
            String imageName = this.name.toLowerCase().replace(" ", "_");
            String imagePathByName = "/org/example/demo/images/" + imageName + ".png";
            
            if (getClass().getResource(imagePathByName) != null) {
                this.sprite = new Image(getClass().getResourceAsStream(imagePathByName));
                return;
            }

            // If no name-specific image, fallback to type-based image
            String imagePathByType = "/org/example/demo/images/" + type.toString().toLowerCase() + ".png";
            if (getClass().getResource(imagePathByType) != null) {
                this.sprite = new Image(getClass().getResourceAsStream(imagePathByType));
            } else {
                System.err.println("Image resource not found for: " + name + " or type " + type);
            }
        } catch (Exception e) {
            System.err.println("Failed to load item image for " + name + ": " + e.getMessage());
        }
    }
    
    @Override
    public void render() {
        // Rendering logic implemented in GameRenderer
    }
    
    @Override
    public void update() {
        // Items typically don't need update logic
    }
    
    /**
     * Use item functionality
     * @return Whether the item was used successfully
     */
    public boolean use() {
        // Implement different usage logic based on item type
        switch (getItemType()) {
            case SEED:
                System.out.println("Planted a tree!");
                return true;
            case TOOL:
                System.out.println("Used a tool!");
                return true;
            case RELIC:
                System.out.println("Activated a natural relic!");
                return true;
            default:
                return false;
        }
    }
    
    // Getter and Setter methods
    public String getName() {
        return name;
    }
    
    public ItemType getItemType() {
        return type;
    }
    
    @Override
    public String getType() {
        return super.getType();
    }
    
    public int getEcoValue() {
        return ecoValue;
    }
} 