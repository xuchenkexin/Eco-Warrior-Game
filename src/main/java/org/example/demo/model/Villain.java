package org.example.demo.model;

/**
 * Represents villain characters in the game
 */
public class Villain extends Character {
    private int pollutionPower;
    private String villainType; // Examples: Toxic Mist, Plastic Plague, Acid Rain Virus, etc.
    
    public Villain(String name, String villainType, int pollutionPower) {
        super(name, 150, 0);
        this.pollutionPower = pollutionPower;
        this.villainType = villainType;
    }
    
    @Override
    public void move(int x, int y) {
        position.move(x, y);
    }
    
    @Override
    public void interact(GameObject object) {
        // Villains interact with objects, potentially damaging the environment
        if (object instanceof Environment) {
            causePollution((Environment) object);
        } else {
            System.out.println(name + " cannot interact with " + object.getType() + ".");
        }
    }
    
    /**
     * Causes pollution to the environment
     * @param environment The affected environment
     */
    public void causePollution(Environment environment) {
        environment.increasePollution(pollutionPower);
        System.out.println(name + " used " + villainType + " to pollute the environment!");
    }
    
    /**
     * Attacks the player
     * @param player Player character
     */
    public void attack(Player player) {
        int damage = (int)(pollutionPower * 0.5);
        player.setHealth(player.getHealth() - damage);
        System.out.println(name + " attacked the player, causing " + damage + " damage!");
    }
    
    // Getter and Setter methods
    public int getPollutionPower() {
        return pollutionPower;
    }
    
    public void setPollutionPower(int pollutionPower) {
        this.pollutionPower = pollutionPower;
    }
    
    public String getVillainType() {
        return villainType;
    }
    
    public void setVillainType(String villainType) {
        this.villainType = villainType;
    }
} 