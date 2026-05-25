package org.example.demo.level;

import org.example.demo.model.*;

/**
 * Level 4: Sacred Mountain
 */
public class SummonDragonLevel extends Level {

    private DragonAltar dragonAltar;

    public SummonDragonLevel() {
        super("Sacred Mountain", "The summit of the Sacred Mountain, an ancient ritual site. After collecting four natural relics, you can perform a summoning ritual here.", 702);
    }

    @Override
    public void initialize() {
        // Create level environment
        Environment mountainEnvironment = new Environment("Sacred Mountain");
        mountainEnvironment.setPollutionLevel(30); // Relatively low pollution
        setEnvironment(mountainEnvironment);

        // Create NPC - Elder
        NPC elder = new NPC("Star Elder", "Ancient Sage");
        elder.addDialogLine("Welcome to the summit of the Sacred Mountain, Eco Guardian. You have come a long way.");
        elder.addDialogLine("Once you have gathered the four natural relics, you can perform the summoning ritual here to awaken the sleeping Sky Dragon.");
        elder.addDialogLine("The Sky Dragon will bring purifying rain to cleanse the world's pollution and give Earth new life.");
        elder.addDialogLine("Even in this sacred place, tourists and pilgrims have left garbage. Before the summoning ritual, please help clean up these pollutants.");
        addNPC(elder);

        // Create summoning ritual challenge
        Challenge summoningChallenge = new Challenge(
                "Sky Dragon Summoning Ritual",
                "Place four natural relics on the altar to perform the summoning ritual.",
                4,
                100
        );

        // Create cleanup challenge
        Challenge cleanupChallenge = new Challenge(
                "Sacred Mountain Purification",
                "Collect and properly sort the trash on the Sacred Mountain.",
                2,
                30
        );

        // Use functional programming to set completion condition
        summoningChallenge.setCompletionCondition(player -> 
            dragonAltar != null && dragonAltar.isDragonSummoned()
        );

        cleanupChallenge.setCompletionCondition(player ->
                player.getEcoPoints() >= 250 // Default already has 200 points, cleaning trash should yield additional 50 points
        );

        // Assign challenge to NPC
        elder.setAssignedChallenge(summoningChallenge);

        // Add challenges to level
        addChallenge(summoningChallenge);
        addChallenge(cleanupChallenge);

        // Generate ritual altar, recycling bins and trash items in the environment
        generateMountainObjects();
        // generateRecycleBins();
        // generateTrashItems();
    }

    /**
     * Generate level objects in the environment
     */
    private void generateMountainObjects() {
        // Ritual altar
        this.dragonAltar = new DragonAltar(300, 300);
        environment.addObject(dragonAltar);

        // Auxiliary items
        environment.addObject(new Item("Purification Water", ItemType.TOOL, 20, 250, 250));
        environment.addObject(new Item("Summoning Spell Book", ItemType.TOOL, 20, 350, 350));

        // Final natural relic
        environment.addObject(new Item("Sacred Mountain Relic", ItemType.RELIC, 50, 400, 400));
    }

    /**
     * Generate recycling bins
     */
    private void generateRecycleBins() {
        /*
        // Create three types of recycling bins for different types of waste
        RecycleBin plasticBin = new RecycleBin("Sacred Mountain Plastic Bin", ItemType.PLASTIC, 400, 150, "/org/example/demo/images/plastic.png");
        RecycleBin metalBin = new RecycleBin("Sacred Mountain Metal Bin", ItemType.METAL, 450, 150, "/org/example/demo/images/metal.png");
        RecycleBin organicBin = new RecycleBin("Sacred Mountain Organic Waste Bin", ItemType.ORGANIC, 500, 150, "/org/example/demo/images/organic.png");

        // Add to environment
        environment.addObject(plasticBin);
        environment.addObject(metalBin);
        environment.addObject(organicBin);

        System.out.println("Recycling bins added to Sacred Mountain environment");
        */
    }

    /**
     * Generate trash items in the environment
     */
    private void generateTrashItems() {
        /*
        // Plastic trash
        environment.addObject(new Item("Pilgrim's Discarded Plastic Bottle", ItemType.PLASTIC, 3, 100, 100));
        environment.addObject(new Item("Plastic Wrapper", ItemType.PLASTIC, 3, 150, 120));
        environment.addObject(new Item("Worn Plastic Prayer Item", ItemType.PLASTIC, 3, 200, 140));

        // Metal trash
        environment.addObject(new Item("Damaged Metal Incense Burner", ItemType.METAL, 3, 120, 180));
        environment.addObject(new Item("Discarded Metal Amulet", ItemType.METAL, 3, 170, 200));
        environment.addObject(new Item("Rusty Bell", ItemType.METAL, 3, 220, 220));

        // Organic trash
        environment.addObject(new Item("Offering Remnants", ItemType.ORGANIC, 3, 140, 240));
        environment.addObject(new Item("Withered Flowers", ItemType.ORGANIC, 3, 190, 260));
        environment.addObject(new Item("Extinguished Incense Ash", ItemType.ORGANIC, 3, 240, 280));
        */
    }

    /**
     * Perform summoning ritual, generate special effects
     * @param player Player
     * @return Ritual result description
     */
    public String performSummoningRitual(Player player) {
        if (dragonAltar == null || !dragonAltar.isDragonSummoned()) {
            return "Summoning failed! The conditions are not met.";
        }

        // Complete all challenges
        for (Challenge challenge : getChallenges()) {
            challenge.setCompleted(true);
        }

        // Set level as completed
        completed = true;

        return "Summoning successful! The Sky Dragon descends, bringing purifying rain. All areas are healed, and Earth regains vitality.";
    }
}