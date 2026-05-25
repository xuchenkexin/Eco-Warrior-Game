package org.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents challenges in the game
 */
public class Challenge {
    private String name;
    private String description;
    private int difficulty;
    private int rewardPoints;
    private boolean completed;
    private Predicate<Player> completionCondition; // Using functional programming to define completion condition
    private List<Item> requiredItems;
    
    public Challenge(String name, String description, int difficulty, int rewardPoints) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.rewardPoints = rewardPoints;
        this.completed = false;
        this.requiredItems = new ArrayList<>();
    }
    
    /**
     * Start the challenge
     * @return Challenge description
     */
    public String start() {
        return "Starting challenge: " + name + "\n" + description;
    }
    
    /**
     * Try to complete the challenge
     * @param player The player
     * @return Whether successfully completed
     */
    public boolean attemptSolve(Player player) {
        if (completed) {
            return false; // Already completed challenges cannot be completed again
        }
        
        // Check completion condition
        if (completionCondition != null && completionCondition.test(player)) {
            completed = true;
            return true;
        }
        
        // Check if player has all required items
        boolean hasAllItems = true;
        for (Item requiredItem : requiredItems) {
            boolean found = player.getInventory().stream()
                    .anyMatch(item -> item.getType() == requiredItem.getType());
            if (!found) {
                hasAllItems = false;
                break;
            }
        }
        
        if (hasAllItems) {
            completed = true;
            return true;
        }
        
        return false;
    }
    
    /**
     * Complete the challenge
     * @return Completion message
     */
    public String complete() {
        completed = true;
        return "Congratulations on completing the challenge: " + name + "\nReceived " + rewardPoints + " eco points!";
    }
    
    // Setter method to configure completion condition
    public void setCompletionCondition(Predicate<Player> condition) {
        this.completionCondition = condition;
    }
    
    // Add required item to complete the challenge
    public void addRequiredItem(Item item) {
        requiredItems.add(item);
    }
    
    // Getter and Setter methods
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public int getRewardPoints() {
        return rewardPoints;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public List<Item> getRequiredItems() {
        return requiredItems;
    }
} 