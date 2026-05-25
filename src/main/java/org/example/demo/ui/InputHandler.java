package org.example.demo.ui;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.example.demo.engine.GameEngine;
import org.example.demo.model.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Class that handles user input
 */
public class InputHandler {
    private Scene scene;
    private GameEngine gameEngine;
    private Set<KeyCode> activeKeys;
    
    // Interaction distance
    private final double INTERACTION_DISTANCE = 50.0;
    
    public InputHandler(Scene scene) {
        this.scene = scene;
        this.gameEngine = GameEngine.getInstance();
        this.activeKeys = new HashSet<>();
        
        // Set up keyboard event listeners
        setupKeyboardListeners();
    }
    
    /**
     * Set up keyboard event listeners
     */
    private void setupKeyboardListeners() {
        // Key press event
        scene.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
            handleKeyPress(event.getCode());
        });
        
        // Key release event
        scene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
        });
    }
    
    /**
     * Handle key press event
     * @param keyCode The key pressed
     */
    private void handleKeyPress(KeyCode keyCode) {
        // Handle different keyboard inputs based on game state
        switch (gameEngine.getGameState()) {
            case TITLE_SCREEN:
                handleTitleScreenInput(keyCode);
                break;
            case PLAYING:
                handlePlayingInput(keyCode);
                break;
            case PAUSED:
                handlePausedInput(keyCode);
                break;
            case DIALOG:
                handleDialogInput(keyCode);
                break;
            case CHALLENGE:
                handleChallengeInput(keyCode);
                break;
            case LEVEL_COMPLETE:
                handleLevelCompleteInput(keyCode);
                break;
            case GAME_OVER:
                handleGameOverInput(keyCode);
                break;
        }
    }
    
    /**
     * Handle title screen input
     * @param keyCode The key pressed
     */
    private void handleTitleScreenInput(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            // Start game
            gameEngine.startGame();
        }
    }
    
    /**
     * Handle in-game input
     * @param keyCode The key pressed
     */
    private void handlePlayingInput(KeyCode keyCode) {
        Player player = gameEngine.getPlayer();
        Position position = player.getPosition();
        Level currentLevel = gameEngine.getCurrentLevel();
        
        // Movement control
        int moveSpeed = 5;
        switch (keyCode) {
            case W:
            case UP:
                position.move(0, -moveSpeed);
                break;
            case S:
            case DOWN:
                position.move(0, moveSpeed);
                break;
            case A:
            case LEFT:
                position.move(-moveSpeed, 0);
                break;
            case D:
            case RIGHT:
                position.move(moveSpeed, 0);
                break;
        }
        
        // Other game controls
        switch (keyCode) {
            case ESCAPE:
                // Pause game
                gameEngine.setGameState(GameState.PAUSED);
                break;
            case E:
                // Interact/interaction
                handleInteraction(player);
                break;
            case I:
                // Open inventory
                System.out.println("Opening inventory");
                break;
            case M:
                // Open map
                System.out.println("Opening map");
                break;
            case N:
                // If eco points reach required points, proceed to next level
                int requiredPoints = currentLevel.getRequiredEcoPoints();
                if (player.getEcoPoints() >= requiredPoints) {
                    System.out.println("Proceeding to next level!");
                    gameEngine.nextLevel();
                } else {
                    System.out.println("Insufficient eco points: " + player.getEcoPoints() + "/" + requiredPoints + " required to proceed to next level.");
                }
                break;
        }
    }
    
    /**
     * Handle player interaction logic
     * @param player Player object
     */
    private void handleInteraction(Player player) {
        System.out.println("Player attempting to interact");
        
        // Get current level and environment
        Level currentLevel = gameEngine.getCurrentLevel();
        if (currentLevel == null || currentLevel.getEnvironment() == null) {
            System.out.println("Current level or environment not loaded");
            return;
        }
        
        Environment environment = currentLevel.getEnvironment();
        Position playerPos = player.getPosition();
        
        // Find interactive objects near the player
        GameObject closestObject = null;
        double closestDistance = INTERACTION_DISTANCE;
        
        for (Iterator<GameObject> it = environment.getObjects().iterator(); it.hasNext();) {
            GameObject obj = it.next();
            if (obj.isInteractive()) {
                double distance = playerPos.distanceTo(obj.getPosition());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestObject = obj;
                }
            }
        }
        
        // Interact with the closest object
        if (closestObject != null) {
            System.out.println("Found interactive object: " + closestObject.getType());
            
            if (closestObject instanceof Item) {
                Item item = (Item) closestObject;
                System.out.println("Picking up item: " + item.getName());
                
                // Add to player inventory and remove from environment
                if (player.collectItem(item)) {
                    environment.removeObject(closestObject);
                    System.out.println("Successfully picked up item, current eco points: " + player.getEcoPoints());
                } else {
                    System.out.println("Inventory full, cannot pick up more items");
                }
            } else if (closestObject instanceof RecycleBin) {
                // Interact with recycling bin
                RecycleBin bin = (RecycleBin) closestObject;
                System.out.println("Interacting with recycling bin: " + bin.getName());
                
                // Simplified logic: find first matching item in inventory and place it
                Item itemToPlace = player.getInventory().stream()
                        .filter(bin::canAccept)
                        .findFirst()
                        .orElse(null);

                if (itemToPlace != null) {
                    player.getInventory().remove(itemToPlace);
                    bin.processItem(player, itemToPlace);
                    System.out.println("Placed " + itemToPlace.getName() + " in the bin.");
                } else {
                    System.out.println("You have no items that fit in this bin.");
                }

            } else if (closestObject instanceof PottingBench) {
                PottingBench bench = (PottingBench) closestObject;
                System.out.println("Interacting with Potting Bench.");
                
                Item wateringCan = player.findItem(item -> "Watering Can".equals(item.getName()));

                if (wateringCan != null && bench.isReadyForWatering()) {
                    // Case 1: Player has can AND bench is ready -> Water the bench
                    bench.water(player, environment);
                } else {
                    // Case 2: Player wants to add items
                    Item itemToAdd = player.getInventory().stream()
                        .filter(item -> item.getItemType() == ItemType.SEED || item.getItemType() == ItemType.SOIL)
                        .findFirst()
                        .orElse(null);
                    
                    if (itemToAdd != null) {
                        // Subcase 2.1: Player has items to add -> Add them
                        if (bench.addItem(itemToAdd)) {
                            player.getInventory().remove(itemToAdd);
                        }
                    } else {
                        // Subcase 2.2: Player has no items to add
                        if (wateringCan != null) {
                            System.out.println("The bench is not ready for watering yet. Add more seeds or soil.");
                        } else {
                            System.out.println("You have no seeds or soil to add.");
                        }
                    }
                }
            } else if (closestObject instanceof DragonAltar) {
                DragonAltar altar = (DragonAltar) closestObject;
                System.out.println("Interacting with Dragon Altar.");

                // Simplified logic: find first relic in inventory and place it.
                Item relicToPlace = player.getInventory().stream()
                        .filter(item -> item.getItemType() == ItemType.RELIC)
                        .findFirst()
                        .orElse(null);
                
                if (relicToPlace != null) {
                    if (altar.placeRelic(relicToPlace)) {
                        player.getInventory().remove(relicToPlace);
                    }
                } else {
                    System.out.println("You have no relics to place on the altar.");
                }
            } else {
                // Player interaction with non-item objects
                player.interact(closestObject);
            }
        } else {
            System.out.println("No interactive objects nearby");
        }
    }
    
    /**
     * Handle paused state input
     * @param keyCode The key pressed
     */
    private void handlePausedInput(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE || keyCode == KeyCode.ESCAPE) {
            // Continue game
            gameEngine.setGameState(GameState.PLAYING);
        }
    }
    
    /**
     * Handle dialog state input
     * @param keyCode The key pressed
     */
    private void handleDialogInput(KeyCode keyCode) {
        if (keyCode == KeyCode.ENTER || keyCode == KeyCode.SPACE) {
            // Continue or end dialog
            gameEngine.setGameState(GameState.PLAYING);
        }
    }
    
    /**
     * Handle challenge state input
     * @param keyCode The key pressed
     */
    private void handleChallengeInput(KeyCode keyCode) {
        if (keyCode == KeyCode.ENTER) {
            // Accept challenge
            System.out.println("Player accepted challenge");
            gameEngine.setGameState(GameState.PLAYING);
        } else if (keyCode == KeyCode.ESCAPE) {
            // Decline challenge
            System.out.println("Player declined challenge");
            gameEngine.setGameState(GameState.PLAYING);
        }
    }
    
    /**
     * Handle level complete state input
     * @param keyCode The key pressed
     */
    private void handleLevelCompleteInput(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE || keyCode == KeyCode.ENTER) {
            // Proceed to next level
            gameEngine.nextLevel();
        }
    }
    
    /**
     * Handle game over state input
     * @param keyCode The key pressed
     */
    private void handleGameOverInput(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE || keyCode == KeyCode.ENTER) {
            // Restart game
            gameEngine.startGame();
        }
    }
    
    /**
     * Get active keys set
     * @return The set of currently pressed keys
     */
    public Set<KeyCode> getActiveKeys() {
        return activeKeys;
    }
} 