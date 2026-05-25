package org.example.demo.level;

import org.example.demo.model.*;

/**
 * Level 3: Suffocating Forest
 */
public class SuffocatingForestLevel extends Level {
    
    public SuffocatingForestLevel() {
        super("Suffocating Forest", "A deforested area with withering vegetation. You need to select and plant appropriate tree species to restore the green vegetation.", 562);
    }
    
    @Override
    public void initialize() {
        // Create level environment
        Environment forestEnvironment = new Environment("Suffocating Forest");
        forestEnvironment.setPollutionLevel(70);
        setEnvironment(forestEnvironment);
        
        // Create NPC - Forest Shaman
        NPC shaman = new NPC("Blue Wind", "Forest Shaman");
        shaman.addDialogLine("Welcome to the Suffocating Forest, Eco Guardian. The once lush forest has been reduced to this state.");
        shaman.addDialogLine("We must plant suitable tree species to restore the forest ecosystem. Different areas require different types of trees.");
        shaman.addDialogLine("Highlands are suitable for pine trees, lowlands for elm trees, and wetlands for willow trees.");
        shaman.addDialogLine("Tourists and loggers have left a lot of garbage. Please help collect and sort this waste.");
        addNPC(shaman);
        
        // Create tree planting challenge
        Challenge plantingChallenge = new Challenge(
            "Forest Restoration",
            "Plant at least 10 trees across three different terrain types.",
            3,
            50
        );
        
        // Create biodiversity challenge
        Challenge biodiversityChallenge = new Challenge(
            "Biodiversity Restoration",
            "Ensure at least three different types of trees are planted, with at least 3 of each type.",
            3,
            40
        );
        
        // Use functional programming to set completion condition
        plantingChallenge.setCompletionCondition(player -> 
            player.getEcoPoints() >= 200 // 20 points per tree, need at least 10
        );
        
        // Assign challenge to NPC
        shaman.setAssignedChallenge(plantingChallenge);
        
        // Add challenges to level
        addChallenge(plantingChallenge);
        addChallenge(biodiversityChallenge);
        
        // Generate seeds, planting areas, trash and recycling bins in the environment
        generateForestObjects();
        // generateRecycleBins(); // Trash removed
        // generateTrashItems(); // Trash removed
    }
    
    /**
     * Generate level objects in the environment
     */
    private void generateForestObjects() {
        // Seeds, Soil, and Potting Bench for the new mechanic
        environment.addObject(new Item("Generic Seed", ItemType.SEED, 5, 100, 150));
        environment.addObject(new Item("Generic Seed", ItemType.SEED, 5, 120, 150));
        environment.addObject(new Item("Generic Seed", ItemType.SEED, 5, 140, 150));

        environment.addObject(new Item("Bag of Soil", ItemType.SOIL, 5, 200, 200));
        environment.addObject(new Item("Bag of Soil", ItemType.SOIL, 5, 220, 200));
        environment.addObject(new Item("Bag of Soil", ItemType.SOIL, 5, 240, 200));

        // Add the potting bench
        environment.addObject(new PottingBench(400, 300));
        
        // Tools
        environment.addObject(new Item("Watering Can", ItemType.TOOL, 10, 250, 150));
        environment.addObject(new Item("Shovel", ItemType.TOOL, 10, 400, 180));
        
        // Natural relic - third level reward
        environment.addObject(new Item("Tree of Life Relic", ItemType.RELIC, 50, 550, 400));
    }
    
    /**
     * Generate recycling bins
     */
    private void generateRecycleBins() {
        /* Trash removed
        // Create three types of recycling bins for different types of waste
        RecycleBin plasticBin = new RecycleBin("Forest Plastic Recycling Point", ItemType.PLASTIC, 50, 400, "/org/example/demo/images/plastic.png");
        RecycleBin metalBin = new RecycleBin("Forest Metal Recycling Point", ItemType.METAL, 150, 400, "/org/example/demo/images/metal.png");
        RecycleBin organicBin = new RecycleBin("Forest Compost Area", ItemType.ORGANIC, 250, 400, "/org/example/demo/images/organic.png");
        
        // Add to environment
        environment.addObject(plasticBin);
        environment.addObject(metalBin);
        environment.addObject(organicBin);
        
        System.out.println("Recycling bins added to Suffocating Forest environment");
        */
    }
    
    /**
     * Generate trash items in the environment
     */
    private void generateTrashItems() {
        /* Trash removed
        // Plastic trash
        environment.addObject(new Item("Discarded Beverage Bottle", ItemType.PLASTIC, 3, 80, 350));
        environment.addObject(new Item("Plastic Packaging", ItemType.PLASTIC, 3, 180, 360));
        environment.addObject(new Item("Plastic Bag", ItemType.PLASTIC, 3, 280, 370));
        
        // Metal trash
        environment.addObject(new Item("Metal Can", ItemType.METAL, 3, 100, 280));
        environment.addObject(new Item("Discarded Tool", ItemType.METAL, 3, 200, 290));
        environment.addObject(new Item("Saw Blade Fragment", ItemType.METAL, 3, 300, 300));
        
        // Organic trash
        environment.addObject(new Item("Campsite Food Waste", ItemType.ORGANIC, 3, 120, 210));
        environment.addObject(new Item("Rotten Leaves", ItemType.ORGANIC, 3, 220, 220));
        environment.addObject(new Item("Damaged Timber", ItemType.ORGANIC, 3, 320, 230));
        */
    }
} 