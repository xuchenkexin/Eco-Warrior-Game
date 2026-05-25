package org.example.demo.level;

import org.example.demo.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

/**
 * Level 2: Rusty City
 */
public class RustyCityLevel extends Level {
    
    public RustyCityLevel() {
        super("Rusty City", "An industrial city severely affected by air, water and soil pollution. You need to collect water samples and submit a pollution report.", 352);
    }
    
    @Override
    public void initialize() {
        // Create level environment
        Environment cityEnvironment = new Environment("Rusty City");
        cityEnvironment.setPollutionLevel(90);
        setEnvironment(cityEnvironment);
        
        // Create NPC - Professor Zhang
        NPC professor = new NPC("Professor Zhang", "Environmental Scientist");
        professor.addDialogLine("Welcome to Rusty City, eco-warrior. The pollution here is extremely severe.");
        professor.addDialogLine("Factory wastewater has contaminated the entire river system. We need you to collect water samples for analysis.");
        professor.addDialogLine("After collecting the samples, please bring them back to the laboratory where we will analyze the data and submit a pollution report.");
        professor.addDialogLine("Additionally, the city's waste sorting system is very poor. Please help collect and sort the garbage.");
        addNPC(professor);
        
        // Create water sample collection challenge
        Challenge waterSampleChallenge = new Challenge(
            "Collect Water Samples",
            "Collect water samples from three different locations along the river.",
            2,
            30
        );
        
        // Create pollution report challenge
        Challenge reportChallenge = new Challenge(
            "Submit Pollution Report",
            "Organize data and submit a pollution report to the Environmental Protection Agency.",
            2,
            40
        );
        
        // Create factory shutdown challenge
        Challenge shutdownFactoryChallenge = new Challenge(
            "Shut Down Polluting Factory",
            "Convince factory management to implement environmental measures or shut down the factory.",
            3,
            50
        );
        
        // Use functional programming to set completion condition
        waterSampleChallenge.setCompletionCondition(player -> 
            player.getInventory().stream().filter(item -> 
                item.getName().contains("Water Sample")).count() >= 3
        );
        
        reportChallenge.setCompletionCondition(player -> 
            waterSampleChallenge.isCompleted() && player.findItem(item -> 
                item.getName().equals("Analysis Report")) != null
        );
        
        // Assign challenge to NPC
        professor.setAssignedChallenge(waterSampleChallenge);
        
        // Add challenges to level
        addChallenge(waterSampleChallenge);
        addChallenge(reportChallenge);
        addChallenge(shutdownFactoryChallenge);
        
        // Generate sampling points, trash and other items in the environment
        generateLevelObjects();
        generateRecycleBins();
        generateTrashItems();
    }
    
    /**
     * Generate level objects in the environment
     */
    private void generateLevelObjects() {
        // Water sampling points
        environment.addObject(new Item("Upstream Sampling Point", ItemType.TOOL, 10, 100, 200));
        environment.addObject(new Item("Midstream Sampling Point", ItemType.TOOL, 10, 300, 350));
        environment.addObject(new Item("Downstream Sampling Point", ItemType.TOOL, 10, 500, 150));
        
        // Tools
        environment.addObject(new Item("Sampler", ItemType.TOOL, 5, 120, 180));
        environment.addObject(new Item("Analysis Equipment", ItemType.TOOL, 15, 350, 200));
        
        // Natural relic - second level reward
        environment.addObject(new Item("Clear Water Relic", ItemType.RELIC, 50, 550, 400));
    }
    
    /**
     * Generate recycling bins
     */
    private void generateRecycleBins() {
        // Create a single recycling bin for all types of waste
        Set<ItemType> acceptedTypes = new HashSet<>(Arrays.asList(ItemType.PLASTIC, ItemType.METAL, ItemType.ORGANIC));
        RecycleBin allInOneBin = new RecycleBin("Mixed Waste Bin", acceptedTypes, 150, 100, "/org/example/demo/images/waste.png");
        
        // Add to environment
        environment.addObject(allInOneBin);
        
        System.out.println("A mixed waste recycling bin added to Rusty City environment");
    }
    
    /**
     * Generate trash items in the environment
     */
    private void generateTrashItems() {
        // Plastic trash
        environment.addObject(new Item("Discarded Plastic Bag", ItemType.PLASTIC, 3, 80, 150));
        environment.addObject(new Item("Plastic Packaging", ItemType.PLASTIC, 3, 180, 170));
        environment.addObject(new Item("Disposable Plastic Utensils", ItemType.PLASTIC, 3, 280, 190));
        
        // Metal trash
        environment.addObject(new Item("Discarded Parts", ItemType.METAL, 3, 120, 220));
        environment.addObject(new Item("Rusty Metal Sheet", ItemType.METAL, 3, 220, 240));
        environment.addObject(new Item("Rusty Pipe", ItemType.METAL, 3, 320, 260));
        
        // Organic trash
        environment.addObject(new Item("Food Waste", ItemType.ORGANIC, 3, 160, 280));
        environment.addObject(new Item("Fallen Leaves", ItemType.ORGANIC, 3, 260, 300));
        environment.addObject(new Item("Rotten Produce", ItemType.ORGANIC, 3, 360, 320));
    }
} 