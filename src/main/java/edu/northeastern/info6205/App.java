package edu.northeastern.info6205;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) {
		LOGGER.info("starting the stage: {}", stage);

        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
		LOGGER.info("javaVersion: {}, javafxVersion: {}", javaVersion, javafxVersion);

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}