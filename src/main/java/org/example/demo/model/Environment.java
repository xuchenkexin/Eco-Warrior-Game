package org.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents the environment in the game
 */
public class Environment extends GameObject {
    private String name;
    private int pollutionLevel; // 0-100
    private List<GameObject> objects;
    private Weather weather;
    private List<Consumer<Integer>> pollutionObservers; // Using functional programming observer pattern
    
    public Environment(String name) {
        super("environment", 0, 0); // Environment positioned at (0,0)
        this.name = name;
        this.pollutionLevel = 50; // Default medium pollution
        this.objects = new ArrayList<>();
        this.weather = Weather.NORMAL;
        this.pollutionObservers = new ArrayList<>();
    }
    
    /**
     * Update environment state
     */
    @Override
    public void update() {
        // Update all objects in the environment
        objects.forEach(GameObject::update);
        
        // Update weather based on pollution level
        updateWeatherBasedOnPollution();
    }
    
    /**
     * Render the environment (environment itself doesn't need rendering, its objects are handled by GameRenderer)
     */
    @Override
    public void render() {
        // Environment rendering is handled by GameRenderer
    }
    
    /**
     * Increase environmental pollution
     * @param amount Amount of pollution increase
     */
    public void increasePollution(int amount) {
        pollutionLevel = Math.min(100, pollutionLevel + amount);
        notifyPollutionObservers();
    }
    
    /**
     * Decrease environmental pollution
     * @param amount Amount of pollution decrease
     */
    public void decreasePollution(int amount) {
        pollutionLevel = Math.max(0, pollutionLevel - amount);
        notifyPollutionObservers();
    }
    
    /**
     * Update weather based on pollution level
     */
    private void updateWeatherBasedOnPollution() {
        if (pollutionLevel >= 80) {
            setWeather(Weather.ACID_RAIN);
        } else if (pollutionLevel >= 60) {
            setWeather(Weather.SMOG);
        } else if (pollutionLevel >= 40) {
            setWeather(Weather.CLOUDY);
        } else if (pollutionLevel >= 20) {
            setWeather(Weather.NORMAL);
        } else {
            setWeather(Weather.SUNNY);
        }
    }
    
    /**
     * Get pollution status description
     * @return Environment status description
     */
    public String getPollutionStatus() {
        if (pollutionLevel >= 80) {
            return "Severely Polluted";
        } else if (pollutionLevel >= 60) {
            return "Moderately Polluted";
        } else if (pollutionLevel >= 40) {
            return "Slightly Polluted";
        } else if (pollutionLevel >= 20) {
            return "Good";
        } else {
            return "Excellent";
        }
    }
    
    /**
     * Add an object to the environment
     * @param object Object to add
     */
    public void addObject(GameObject object) {
        objects.add(object);
    }
    
    /**
     * Remove an object from the environment
     * @param object Object to remove
     */
    public void removeObject(GameObject object) {
        objects.remove(object);
    }
    
    /**
     * Add pollution observer
     * @param observer Observer function
     */
    public void addPollutionObserver(Consumer<Integer> observer) {
        pollutionObservers.add(observer);
    }
    
    /**
     * Notify all pollution observers
     */
    private void notifyPollutionObservers() {
        pollutionObservers.forEach(observer -> observer.accept(pollutionLevel));
    }
    
    // Getter and Setter methods
    public String getName() {
        return name;
    }
    
    public int getPollutionLevel() {
        return pollutionLevel;
    }
    
    public void setPollutionLevel(int pollutionLevel) {
        this.pollutionLevel = Math.min(100, Math.max(0, pollutionLevel));
        notifyPollutionObservers();
    }
    
    public List<GameObject> getObjects() {
        return objects;
    }
    
    public Weather getWeather() {
        return weather;
    }
    
    public void setWeather(Weather weather) {
        this.weather = weather;
    }
} 