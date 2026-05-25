package org.example.demo.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Scoring system for the game, using the observer pattern to notify score changes
 */
public class ScoringSystem {
    private int score;
    private int highScore;
    private List<Consumer<Integer>> scoreObservers;

    public ScoringSystem() {
        this.score = 0;
        this.highScore = 0;
        this.scoreObservers = new ArrayList<>();
    }

    /**
     * Add points to the score
     * @param points The points to add
     */
    public void addScore(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
        }
        notifyScoreObservers();
    }

    /**
     * Subtract points from the score
     * @param points The points to subtract
     */
    public void subtractScore(int points) {
        score = Math.max(0, score - points);
        notifyScoreObservers();
    }

    /**
     * Add a score observer
     * @param observer The observer function
     */
    public void addScoreObserver(Consumer<Integer> observer) {
        scoreObservers.add(observer);
    }

    /**
     * Notify all score observers
     */
    private void notifyScoreObservers() {
        scoreObservers.forEach(observer -> observer.accept(score));
    }

    /**
     * Reset the score
     */
    public void resetScore() {
        score = 0;
        notifyScoreObservers();
    }

    /**
     * Get the performance rating based on the current score
     * @return The performance rating (A/B/C/D/F)
     */
    public String getRating() {
        if (score >= 500) {
            return "A";
        } else if (score >= 400) {
            return "B";
        } else if (score >= 300) {
            return "C";
        } else if (score >= 200) {
            return "D";
        } else {
            return "F";
        }
    }

    // Getter methods
    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
}