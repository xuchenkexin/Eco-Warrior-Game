package org.example.demo.model;

/**
 * 角色基类，所有游戏中的角色都继承自此类
 */
public abstract class Character {
    protected String name;
    protected int health;
    protected int ecoPoints;
    protected Position position;

    public Character(String name, int health, int ecoPoints) {
        this.name = name;
        this.health = health;
        this.ecoPoints = ecoPoints;
        this.position = new Position(0, 0);
    }

    public abstract void move(int x, int y);
    
    public abstract void interact(GameObject object);

    // Getter和Setter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEcoPoints() {
        return ecoPoints;
    }

    public void setEcoPoints(int ecoPoints) {
        this.ecoPoints = ecoPoints;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
} 