package org.example.demo.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.example.demo.engine.GameEngine;
import org.example.demo.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for game graphics rendering
 */
public class GameRenderer {
    private Canvas canvas;
    private GraphicsContext gc;
    private GameEngine gameEngine;

    // Cache game resources
    private Image backgroundImage;
    private Image playerImage;
    private Map<String, Image> levelBackgrounds;

    public GameRenderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.gameEngine = GameEngine.getInstance();

        // Set game engine renderer
        gameEngine.setRenderer(this);

        // Initialize level backgrounds map
        this.levelBackgrounds = new HashMap<>();

        // Load resources
        loadResources();
    }

    /**
     * Load game resources
     */
    private void loadResources() {
        try {
            // Try to load background image, if not exists use default color
            try {
                backgroundImage = new Image(getClass().getResourceAsStream("/org/example/demo/images/background.png"));
            } catch (Exception e) {
                System.err.println("Failed to load background image, using default background: " + e.getMessage());
            }

            // Try to load player image, if not exists use default shape
            try {
                playerImage = new Image(getClass().getResourceAsStream("/org/example/demo/images/player.png"));
            } catch (Exception e) {
                System.err.println("Failed to load player image, using default shape: " + e.getMessage());
            }

            // Load level-specific background images
            loadLevelBackgrounds();

        } catch (Exception e) {
            System.err.println("Error loading resources: " + e.getMessage());
        }
    }

    /**
     * Load background images for each level
     */
    private void loadLevelBackgrounds() {
        // Load level-specific background images
        try {
            // Level 1: Polluted Bay
            Image pollutedBayBg = new Image(getClass().getResourceAsStream("/org/example/demo/images/polluted_bay_bg.png"));
            levelBackgrounds.put("Polluted Bay", pollutedBayBg);

            // Level 2: Rusty City
            Image rustyCityBg = new Image(getClass().getResourceAsStream("/org/example/demo/images/rusty_city_bg.png"));
            levelBackgrounds.put("Rusty City", rustyCityBg);

            // Level 3: Suffocating Forest
            Image suffocatingForestBg = new Image(getClass().getResourceAsStream("/org/example/demo/images/suffocating_forest_bg.png"));
            levelBackgrounds.put("Suffocating Forest", suffocatingForestBg);

            // Level 4: Sacred Mountain
            Image sacredMountainBg = new Image(getClass().getResourceAsStream("/org/example/demo/images/sacred_mountain_bg.png"));
            levelBackgrounds.put("Sacred Mountain", sacredMountainBg);

            System.out.println("Successfully loaded level background images");

        } catch (Exception e) {
            System.err.println("Failed to load level backgrounds: " + e.getMessage());
        }
    }

    /**
     * Render the game
     */
    public void render() {
        try {
            // Clear canvas
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Render different screens based on game state
            switch (gameEngine.getGameState()) {
                case TITLE_SCREEN:
                    renderTitleScreen();
                    break;
                case PLAYING:
                    renderGamePlay();
                    break;
                case PAUSED:
                    renderGamePlay();
                    renderPauseScreen();
                    break;
                case DIALOG:
                    renderGamePlay();
                    renderDialog();
                    break;
                case CHALLENGE:
                    renderGamePlay();
                    renderChallenge();
                    break;
                case LEVEL_COMPLETE:
                    renderLevelComplete();
                    break;
                case GAME_OVER:
                    renderGameOver();
                    break;
            }
        } catch (Exception e) {
            // Error during rendering, display error message
            System.err.println("Error during rendering: " + e.getMessage());
            e.printStackTrace();

            // Display error message on screen
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 16));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("Rendering error: " + e.getMessage(),
                    canvas.getWidth() / 2, canvas.getHeight() / 2);
        }
    }

    /**
     * Render title screen
     */
    private void renderTitleScreen() {
        // Draw background
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            gc.setFill(Color.GREEN.darker());
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        // Draw game title
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Eco Guardian", canvas.getWidth() / 2, canvas.getHeight() / 3);

        // Draw subtitle
        gc.setFont(new Font("Arial", 24));
        gc.fillText("Purify Earth, Save the World", canvas.getWidth() / 2, canvas.getHeight() / 3 + 50);

        // Draw start game prompt
        gc.setFont(new Font("Arial", 18));
        gc.fillText("Press Space to Start Game", canvas.getWidth() / 2, canvas.getHeight() * 2 / 3);
    }

    /**
     * Render game play
     */
    private void renderGamePlay() {
        try {
            // Get current level
            Level currentLevel = gameEngine.getCurrentLevel();
            if (currentLevel == null || currentLevel.getEnvironment() == null) {
                // Level not initialized, draw default background
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                return;
            }

            Environment environment = currentLevel.getEnvironment();

            // Draw background
            renderBackground(environment);

            // Draw environment objects
            renderEnvironmentObjects(environment);

            // Draw NPCs
            renderNPCs(currentLevel);

            // Draw player
            renderPlayer();

            // Draw UI information
            renderUI();
        } catch (Exception e) {
            System.err.println("Error rendering game screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Render background
     * @param environment Current environment
     */
    private void renderBackground(Environment environment) {
        // Try to use level-specific background if available
        String environmentName = environment.getName();
        Image levelBg = levelBackgrounds.get(environmentName);

        if (levelBg != null) {
            // Use level-specific background
            gc.drawImage(levelBg, 0, 0, canvas.getWidth(), canvas.getHeight());
            return;
        }

        // Adjust background color based on pollution level and weather
        Color backgroundColor;
        switch (environment.getWeather()) {
            case SUNNY:
                backgroundColor = Color.rgb(135, 206, 235); // Sky blue
                break;
            case NORMAL:
                backgroundColor = Color.rgb(135, 206, 235).darker(); // Darker sky blue
                break;
            case CLOUDY:
                backgroundColor = Color.rgb(169, 169, 169); // Gray
                break;
            case SMOG:
                backgroundColor = Color.rgb(128, 128, 128); // Dark gray
                break;
            case ACID_RAIN:
                backgroundColor = Color.rgb(58, 95, 11); // Dark green
                break;
            default:
                backgroundColor = Color.rgb(135, 206, 235);
        }

        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Render environment objects
     * @param environment Current environment
     */
    private void renderEnvironmentObjects(Environment environment) {
        for (GameObject obj : environment.getObjects()) {
            Image sprite = obj.getSprite();
            Position pos = obj.getPosition();
            if (sprite != null) {
                if (obj instanceof Item) {
                    Item item = (Item) obj;
                    ItemType type = item.getItemType();
                    if (type == ItemType.PLASTIC || type == ItemType.METAL || type == ItemType.ORGANIC) {
                        // Render waste items larger
                        gc.drawImage(sprite, pos.getX(), pos.getY(), 48, 48);
                    } else if (type == ItemType.SEED || type == ItemType.GROWN_SEED || type == ItemType.SOIL) {
                        // Render planting-related items larger
                        gc.drawImage(sprite, pos.getX(), pos.getY(), 48, 48);
                    } else {
                        // Render other items at default size
                        gc.drawImage(sprite, pos.getX(), pos.getY(), 32, 32);
                    }
                } else {
                    // Default size for non-item objects like recycle bins if they have a sprite
                    gc.drawImage(sprite, pos.getX(), pos.getY(), 32, 32);
                }
            } else {
                // If no sprite, draw a simple block
                if (obj instanceof RecycleBin) {
                    renderRecycleBin((RecycleBin) obj);
                } else if (obj instanceof PottingBench) {
                    renderPottingBench((PottingBench) obj);
                } else if (obj instanceof Item) {
                    // Use different colors based on item type
                    Item item = (Item) obj;
                    switch (item.getItemType()) {
                        case SEED:
                        case SOIL:
                        case GROWN_SEED:
                            gc.setFill(Color.GREEN);
                            break;
                        case TOOL:
                            gc.setFill(Color.BLUE);
                            break;
                        case RELIC:
                            gc.setFill(Color.GOLD);
                            break;
                        case WASTE:
                        case PLASTIC:
                            gc.setFill(Color.DODGERBLUE); // Plastic is blue
                            break;
                        case METAL:
                            gc.setFill(Color.YELLOW); // Metal is yellow
                            break;
                        case ORGANIC:
                            gc.setFill(Color.FORESTGREEN); // Organic is green
                            break;
                        default:
                            gc.setFill(Color.CHOCOLATE);
                    }
                    gc.fillRect(pos.getX(), pos.getY(), 32, 32);
                } else {
                    gc.setFill(Color.CHOCOLATE);
                    gc.fillRect(pos.getX(), pos.getY(), 32, 32);
                }
            }

            if (obj.isInteractive()) {
                // Draw name for interactive objects
                String nameToRender = null;
                if (obj instanceof Item) {
                    nameToRender = ((Item) obj).getName();
                } else if (obj instanceof RecycleBin) {
                    nameToRender = ((RecycleBin) obj).getName();
                }
                
                if (nameToRender != null) {
                    gc.setFill(Color.WHITE);
                    gc.setFont(new Font("Arial", 12));
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.fillText(nameToRender, pos.getX() + 16, pos.getY() - 5);
                }
            }
        }
    }

    private void renderNPCs(Level level) {
        for (NPC npc : level.getNpcs()) {
            Position pos = npc.getPosition();
            // Draw NPC (e.g., as a different colored block)
            gc.setFill(Color.PURPLE);
            gc.fillRect(pos.getX(), pos.getY(), 32, 32);

            // Draw NPC name
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 12));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(npc.getName(), pos.getX() + 16, pos.getY() - 5);
        }
    }

    /**
     * Render recycle bin
     * @param bin Recycle bin to render
     */
    private void renderRecycleBin(RecycleBin bin) {
        gc.setFill(bin.getBinColor());
        gc.fillRect(bin.getPosition().getX(), bin.getPosition().getY(), 48, 48);
    }

    private void renderPottingBench(PottingBench bench) {
        if (bench.isEnlarged()) {
            // State 3: Watered and enlarged
            gc.setFill(Color.GREEN.darker());
            gc.fillRect(bench.getPosition().getX() - 10, bench.getPosition().getY() - 10, 68, 68);
        } else if (bench.isReadyForWatering()) {
            // State 2: Ready for watering
            gc.setFill(Color.GREEN);
            gc.fillRect(bench.getPosition().getX(), bench.getPosition().getY(), 48, 48);
        } else {
            // State 1: Initial state
            gc.setFill(Color.BLACK);
            gc.fillRect(bench.getPosition().getX(), bench.getPosition().getY(), 48, 48);
        }
    }

    /**
     * Render player
     */
    private void renderPlayer() {
        Player player = gameEngine.getPlayer();
        Position pos = player.getPosition();

        if (playerImage != null) {
            gc.drawImage(playerImage, pos.getX(), pos.getY(), 48, 48);
        } else {
            // If no player image, draw a simple circle
            gc.setFill(Color.BLUE);
            gc.fillOval(pos.getX(), pos.getY(), 48, 48);
        }
    }

    /**
     * Render UI information
     */
    private void renderUI() {
        // Draw player UI
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Eco Points: " + gameEngine.getPlayer().getEcoPoints(), 20, 40);
        gc.fillText("Health: " + gameEngine.getPlayer().getHealth(), 20, 70);

        // Draw level information
        gc.setFont(new Font("Arial", 24));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Level: " + gameEngine.getCurrentLevel().getName(), canvas.getWidth() / 2, 40);
        gc.fillText("Next Level Requires: " + gameEngine.getCurrentLevel().getRequiredEcoPoints() + " Eco Points", canvas.getWidth() / 2, 80);

        // Draw score
        gc.setFont(new Font("Arial", 20));
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("Score: " + gameEngine.getScoringSystem().getScore(), canvas.getWidth() - 100, 40);

        // Render "Press N" prompt at the bottom if level is completable
        if (gameEngine.canGoToNextLevel()) {
            gc.setFill(Color.YELLOW);
            gc.setFont(new Font("Arial", 30));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("Press N to enter next level!", canvas.getWidth() / 2, canvas.getHeight() - 50);
        }
    }

    /**
     * Render pause screen
     */
    private void renderPauseScreen() {
        // Semi-transparent background
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Pause text
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Game Paused", canvas.getWidth() / 2, canvas.getHeight() / 2);

        // Control tip
        gc.setFont(new Font("Arial", 18));
        gc.fillText("Press Space to Continue", canvas.getWidth() / 2, canvas.getHeight() / 2 + 40);
    }

    /**
     * Render dialog box
     */
    private void renderDialog() {
        // Dialog background
        gc.setFill(Color.rgb(0, 0, 100, 0.8));
        gc.fillRect(50, canvas.getHeight() - 150, canvas.getWidth() - 100, 120);

        // Dialog text
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 16));
        gc.setTextAlign(TextAlignment.LEFT);

        // Dialog text should come from current level NPC
        String dialogText = "NPC: This is an example dialog text. In the actual game, this will display character conversations.";

        // Line wrapping for long text (simplified)
        gc.fillText(dialogText, 70, canvas.getHeight() - 120);

        // Prompt to continue dialog
        gc.setFont(new Font("Arial", 12));
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("Press Enter to continue...", canvas.getWidth() - 70, canvas.getHeight() - 40);
    }

    /**
     * Render challenge interface
     */
    private void renderChallenge() {
        // Challenge background
        gc.setFill(Color.rgb(100, 0, 0, 0.8));
        gc.fillRect(50, 50, canvas.getWidth() - 100, 150);

        // Challenge title
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Environmental Challenge", canvas.getWidth() / 2, 80);
        
        // Challenge description
        gc.setFont(new Font("Arial", 16));

        // Challenge description should come from current level
        String challengeDescription = "Challenge Description: This is an example challenge description. In the actual game, this will display specific challenge tasks and objectives.";

        // Line wrapping (simplified)
        gc.fillText(challengeDescription, canvas.getWidth() / 2, 120);

        // Prompt to accept challenge
        gc.setFont(new Font("Arial", 14));
        gc.fillText("Press Enter to accept challenge", canvas.getWidth() / 2, 170);
    }

    /**
     * Render level complete screen
     */
    private void renderLevelComplete() {
        // Semi-transparent background
        gc.setFill(Color.rgb(0, 100, 0, 0.7));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Level complete text
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Level Complete!", canvas.getWidth() / 2, canvas.getHeight() / 2 - 40);

        // Display earned eco points
        gc.setFont(new Font("Arial", 24));
        gc.fillText("Eco Points Earned: +50", canvas.getWidth() / 2, canvas.getHeight() / 2 + 10);

        // Prompt for next level
        gc.setFont(new Font("Arial", 18));
        gc.fillText("Press Space to enter next level", canvas.getWidth() / 2, canvas.getHeight() / 2 + 60);
    }

    /**
     * Render game over screen
     */
    private void renderGameOver() {
        // Semi-transparent background
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Game over text
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Game Over", canvas.getWidth() / 2, canvas.getHeight() / 3);

        // Display final score and rating
        gc.setFont(new Font("Arial", 24));
        gc.fillText("Final Score: " + gameEngine.getScoringSystem().getScore(),
                canvas.getWidth() / 2, canvas.getHeight() / 2 - 20);
        gc.fillText("Eco Points: " + gameEngine.getPlayer().getEcoPoints(),
                canvas.getWidth() / 2, canvas.getHeight() / 2 + 20);
        gc.fillText("Rating: " + gameEngine.getScoringSystem().getRating(),
                canvas.getWidth() / 2, canvas.getHeight() / 2 + 60);

        // Prompt to restart
        gc.setFont(new Font("Arial", 18));
        gc.fillText("Press Space to restart game", canvas.getWidth() / 2, canvas.getHeight() * 2 / 3);
    }
}