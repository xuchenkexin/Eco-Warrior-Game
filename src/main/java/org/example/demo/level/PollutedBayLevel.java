package org.example.demo.level;

import org.example.demo.model.*;

/**
 * Level 1: Polluted Bay
 */
public class PollutedBayLevel extends Level {
    
    public PollutedBayLevel() {
        super("Polluted Bay", "The beach is covered with plastic, oil barrels, and dead marine life. You need to clean up the beach and restore the marine ecosystem.", 130);
    }
    
    @Override
    public void initialize() {
        // Create level environment
        Environment bayEnvironment = new Environment("Polluted Bay");
        bayEnvironment.setPollutionLevel(80);
        setEnvironment(bayEnvironment);
        
        // Create NPC - Old Ranger
        NPC ranger = new NPC("Old Li", "Veteran Ranger");
        ranger.addDialogLine("Welcome to Polluted Bay, young eco-warrior. This was once a beautiful vacation spot, now ruined by pollution.");
        ranger.addDialogLine("We need your help to clean up the trash on the beach and sort it properly for recycling.");
        ranger.addDialogLine("Remember: Blue bins are for plastic, yellow bins for metal, and green bins for organic waste.");
        addNPC(ranger);
        
        // Create recycling challenge
        Challenge recyclingChallenge = new Challenge(
            "Waste Sorting",
            "Collect 10 pieces of trash and sort them correctly into recycling bins.", 
            1, 
            30
        );
        
        // Use functional programming to set completion condition
        recyclingChallenge.setCompletionCondition(player -> 
            player.getEcoPoints() >= 30 // 3 points per correctly sorted trash item, need 10
        );
        
        // Assign challenge to NPC
        ranger.setAssignedChallenge(recyclingChallenge);
        
        // Add challenge to level
        addChallenge(recyclingChallenge);
        
        // Generate trash items and recycling bins in the environment
        generateTrashItems();
        generateRecycleBins();
    }
    
    /**
     * Generate trash items in the environment
     */
    private void generateTrashItems() {
        // Plastic trash
        environment.addObject(new Item("Plastic Bag", ItemType.PLASTIC, 3, 100, 150));
        environment.addObject(new Item("Plastic Bottle", ItemType.PLASTIC, 3, 200, 130));
        environment.addObject(new Item("Plastic Straw", ItemType.PLASTIC, 3, 150, 200));
        environment.addObject(new Item("Plastic Cup", ItemType.PLASTIC, 3, 300, 180));
        
        // Metal trash
        environment.addObject(new Item("Aluminum Can", ItemType.METAL, 3, 250, 220));
        environment.addObject(new Item("Metal Bottle Cap", ItemType.METAL, 3, 180, 250));
        environment.addObject(new Item("Wire", ItemType.METAL, 3, 220, 300));
        
        // Organic trash
        environment.addObject(new Item("Rotten Seaweed", ItemType.ORGANIC, 3, 350, 150));
        environment.addObject(new Item("Food Waste", ItemType.ORGANIC, 3, 400, 200));
        environment.addObject(new Item("Leaves", ItemType.ORGANIC, 3, 270, 350));
        
        // Natural relic - first level reward
        environment.addObject(new Item("Coconut Tree Life Relic", ItemType.RELIC, 50, 450, 400));
    }
    
    /**
     * Generate recycling bins
     */
    private void generateRecycleBins() {
        // Create three types of recycling bins for different types of waste
        RecycleBin plasticBin = new RecycleBin("Plastic Recycling Bin", ItemType.PLASTIC, 500, 100, "/org/example/demo/images/plastic_bin.png");
        RecycleBin metalBin = new RecycleBin("Metal Recycling Bin", ItemType.METAL, 600, 200, "/org/example/demo/images/metal_bin.png");
        RecycleBin organicBin = new RecycleBin("Organic Waste Bin", ItemType.ORGANIC, 700, 300, "/org/example/demo/images/organic_bin.png");
        
        // Add to environment
        environment.addObject(plasticBin);
        environment.addObject(metalBin);
        environment.addObject(organicBin);
        
        System.out.println("Recycling bins added to game environment");
    }
} 