package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.demo.engine.GameEngine;

import java.io.IOException;

/**
 * Eco Guardian Game Main Class
 */
public class EcoGuardianGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load main game interface
        FXMLLoader fxmlLoader = new FXMLLoader(EcoGuardianGame.class.getResource("game-view.fxml"));
        BorderPane gamePane = fxmlLoader.load();
        
        // Create scene
        Scene scene = new Scene(gamePane);
        
        // Set window title and scene
        stage.setTitle("Eco Guardian - Save the Earth");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
        
        // Set window close event
        stage.setOnCloseRequest(event -> {
            // Save game progress
            GameEngine.getInstance().saveProgress();
        });
    }

    public static void main(String[] args) {
        launch();
    }
} 