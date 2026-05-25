package org.example.demo.engine;

import javafx.animation.AnimationTimer;
import org.example.demo.level.*;
import org.example.demo.model.*;
import org.example.demo.ui.GameRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Game engine class, responsible for managing game state and logic
 */
public class GameEngine {
    private Player player;
    private List<Level> levels;
    private int currentLevelIndex;
    private ScoringSystem scoringSystem;
    private GameState gameState;
    private GameRenderer renderer;
    private List<Consumer<GameState>> stateObservers;
    private AnimationTimer gameLoop;
    private boolean canGoToNextLevel = false;

    // Singleton pattern
    private static GameEngine instance;

    private GameEngine() {
        this.player = new Player("Eco Guardian");
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
        this.scoringSystem = new ScoringSystem();
        this.gameState = GameState.TITLE_SCREEN;
        this.stateObservers = new ArrayList<>();

        // Initialize game levels
        initializeLevels();
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    /**
     * Initialize game levels
     */
    private void initializeLevels() {
        // Add four levels
        levels.add(new PollutedBayLevel());
        levels.add(new RustyCityLevel());
        levels.add(new SuffocatingForestLevel());
        levels.add(new SummonDragonLevel());
    }

    /**
     * Start the game
     */
    public void startGame() {
        gameState = GameState.PLAYING;
        loadCurrentLevel();
        notifyStateObservers();

        // Start the game loop
        startGameLoop();
    }

    /**
     * Start the game loop
     */
    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                if (renderer != null) {
                    renderer.render();
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Update game state
     */
    public void update() {
        if (gameState == GameState.PLAYING) {
            // Update the current level environment
            getCurrentLevel().getEnvironment().update();

            // Check if the level can be completed
            this.canGoToNextLevel = getCurrentLevel().canComplete(player);
        }
    }

    /**
     * Load the current level
     */
    public void loadCurrentLevel() {
        Level level = getCurrentLevel();
        level.initialize();
        System.out.println(level.start());
    }

    /**
     * Move to the next level
     */
    public void nextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            // Clear non-relic items from player's inventory before proceeding
            player.getInventory().removeIf(item -> item.getItemType() != ItemType.RELIC);
            System.out.println("Cleared non-essential items from inventory.");

            currentLevelIndex++;
            loadCurrentLevel();
            // Reward the player for completing the level
            player.setEcoPoints(player.getEcoPoints() + 50);
            scoringSystem.addScore(100);
        } else {
            // Game completed
            gameState = GameState.GAME_OVER;
            System.out.println("Congratulations! You have completed all levels! The Earth has been purified!");
        }
        notifyStateObservers();
    }

    /**
     * Save game progress (simplified version)
     */
    public void saveProgress() {
        System.out.println("Game progress has been saved!");
        // Actual progress saving code should be here
    }

    /**
     * Add a game state observer
     * @param observer Observer function
     */
    public void addStateObserver(Consumer<GameState> observer) {
        stateObservers.add(observer);
    }

    /**
     * Notify all game state observers
     */
    private void notifyStateObservers() {
        stateObservers.forEach(observer -> observer.accept(gameState));
    }

    // Getter and Setter methods
    public Player getPlayer() {
        return player;
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public ScoringSystem getScoringSystem() {
        return scoringSystem;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyStateObservers();
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public boolean canGoToNextLevel() {
        return canGoToNextLevel;
    }
}