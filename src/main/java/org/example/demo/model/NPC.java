package org.example.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents non-player characters in the game
 */
public class NPC extends Character {
    private List<String> dialogLines;
    private Challenge assignedChallenge;
    private String role; // Examples: Old Ranger, Professor, Forest Shaman, etc.
    
    public NPC(String name, String role) {
        super(name, 100, 0);
        this.dialogLines = new ArrayList<>();
        this.role = role;
    }
    
    @Override
    public void move(int x, int y) {
        // NPCs typically don't move or have specific movement patterns
    }
    
    @Override
    public void interact(GameObject object) {
        // NPCs typically don't interact with objects
    }
    
    /**
     * Start dialog with NPC
     * @return Dialog text
     */
    public String startDialog() {
        if (dialogLines.isEmpty()) {
            return name + ": Hello, traveler!";
        }
        return name + ": " + dialogLines.get(0);
    }
    
    /**
     * Get next dialog line
     * @param index Current dialog index
     * @return Next dialog text, or null if no more dialog
     */
    public String getNextDialog(int index) {
        if (index + 1 < dialogLines.size()) {
            return name + ": " + dialogLines.get(index + 1);
        }
        return null;
    }
    
    /**
     * Assign challenge to player
     * @return Assigned challenge
     */
    public Challenge giveChallenge() {
        return assignedChallenge;
    }
    
    // Getter and Setter methods
    public void addDialogLine(String line) {
        dialogLines.add(line);
    }
    
    public List<String> getDialogLines() {
        return dialogLines;
    }
    
    public void setAssignedChallenge(Challenge challenge) {
        this.assignedChallenge = challenge;
    }
    
    public Challenge getAssignedChallenge() {
        return assignedChallenge;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
} 