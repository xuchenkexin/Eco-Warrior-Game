package org.example.demo.model;

import javafx.scene.image.Image;

/**
 * 游戏中所有对象的基类
 */
public abstract class GameObject {
    protected Position position;
    protected String type;
    protected Image sprite;
    protected boolean interactive;
    
    public GameObject(String type, int x, int y) {
        this.type = type;
        this.position = new Position(x, y);
        this.interactive = false;
    }
    
    public abstract void render();
    
    public abstract void update();
    
    public String getType() {
        return type;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public boolean isInteractive() {
        return interactive;
    }
    
    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }
    
    public Image getSprite() {
        return sprite;
    }
    
    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }
} 