package org.example.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import org.example.demo.engine.GameEngine;
import org.example.demo.model.GameState;
import org.example.demo.ui.GameRenderer;
import org.example.demo.ui.InputHandler;

/**
 * Main game controller, responsible for connecting UI and game logic
 */
public class GameController {
    @FXML
    private Canvas gameCanvas;

    @FXML
    private BorderPane gamePane;

    private GameEngine gameEngine;
    private GameRenderer renderer;
    private InputHandler inputHandler;

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        // Get the game engine instance
        gameEngine = GameEngine.getInstance();

        // Set the Canvas size to change with the window
        gameCanvas.widthProperty().bind(gamePane.widthProperty());
        gameCanvas.heightProperty().bind(gamePane.heightProperty());

        // Initialize the game renderer
        renderer = new GameRenderer(gameCanvas);

        // Listen for Canvas size changes to ensure correct content layout
        gameCanvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            renderer.render();
        });

        gameCanvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            renderer.render();
        });

        // Set the game state observer
        gameEngine.addStateObserver(this::onGameStateChanged);

        // Start the game loop
        gamePane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                // Initialize the input handler
                inputHandler = new InputHandler(newScene);

                // Set the initial state to the title screen
                gameEngine.setGameState(GameState.TITLE_SCREEN);
            }
        });
    }

    /**
     * Called when the game state changes
     * @param newState The new game state
     */
    private void onGameStateChanged(GameState newState) {
        // Update the UI based on the game state
        switch (newState) {
            case TITLE_SCREEN:
                System.out.println("Entering title screen");
                break;
            case PLAYING:
                System.out.println("Game is playing");
                break;
            case PAUSED:
                System.out.println("Game is paused");
                break;
            case DIALOG:
                System.out.println("Displaying dialog");
                break;
            case CHALLENGE:
                System.out.println("Displaying challenge");
                break;
            case LEVEL_COMPLETE:
                System.out.println("Level complete");
                break;
            case GAME_OVER:
                System.out.println("Game over");
                break;
        }

        // Update rendering
        renderer.render();
    }

    /**
     * Start button click event
     */
    @FXML
    private void onStartButtonClick() {
        gameEngine.startGame();
    }

    /**
     * Pause button click event
     */
    @FXML
    private void onPauseButtonClick() {
        if (gameEngine.getGameState() == GameState.PLAYING) {
            gameEngine.setGameState(GameState.PAUSED);
        } else if (gameEngine.getGameState() == GameState.PAUSED) {
            gameEngine.setGameState(GameState.PLAYING);
        }
    }

    /**
     * Exit button click event
     */
    @FXML
    private void onExitButtonClick() {
        // Save game progress
        gameEngine.saveProgress();
        // Exit the game
        System.exit(0);
    }
}