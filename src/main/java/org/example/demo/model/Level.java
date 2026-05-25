package org.example.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game level
 */
public abstract class Level {
    protected String name;
    protected String description;
    protected List<Challenge> challenges;
    protected Environment environment;
    protected List<NPC> npcs;
    protected boolean completed;
    protected int requiredEcoPoints;
    
    public Level(String name, String description, int requiredEcoPoints) {
        this.name = name;
        this.description = description;
        this.challenges = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.completed = false;
        this.requiredEcoPoints = requiredEcoPoints;
    }
    
    /**
     * Initialize the level
     */
    public abstract void initialize();
    
    /**
     * Start the level
     * @return Level introduction
     */
    public String start() {
        return "Starting level: " + name + "\n" + description;
    }
    
    /**
     * Check if level can be completed
     * @param player Player
     * @return Whether the level can be completed
     */
    public boolean canComplete(Player player) {
        // Use the requiredEcoPoints field set in the constructor
        return player.getEcoPoints() >= this.requiredEcoPoints;
    }
    
    /**
     * Complete the level
     * @return Completion message
     */
    public String complete() {
        completed = true;
        return "Congratulations on completing the level: " + name + "\nContinue to the next level!";
    }
    
    /**
     * Add challenge to level
     * @param challenge Challenge
     */
    public void addChallenge(Challenge challenge) {
        challenges.add(challenge);
    }
    
    /**
     * Add NPC to level
     * @param npc NPC character
     */
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }
    
    /**
     * Set level environment
     * @param environment Environment
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<Challenge> getChallenges() {
        return challenges;
    }
    
    public Environment getEnvironment() {
        return environment;
    }
    
    public List<NPC> getNpcs() {
        return npcs;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public int getRequiredEcoPoints() {
        return requiredEcoPoints;
    }
} 