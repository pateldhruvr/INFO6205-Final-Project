package edu.northeastern.info6205.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DashboardPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardPageController.class);
	
	@FXML
	private Button importDataButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.debug("Controller initialized");
		
		initializeListeners();
	}
	
	private void initializeListeners() {
		LOGGER.trace("Initialising the listeners");
		
		initializeImportDataButtonClickListener();
		
		LOGGER.trace("all the listeners have been initialized");
	}
	
	private void initializeImportDataButtonClickListener() {
		LOGGER.trace("initializing import Data button click listener");
		
		importDataButton.setOnMouseClicked(event -> handleImportFileMenuClick());
		
		LOGGER.trace("import Data click listener has been initialized");
	}

	private void handleImportFileMenuClick() {
		LOGGER.trace("import file menu button has been clicked");
		
		String userHomeProperty = System.getProperty("user.home");
		LOGGER.trace("userHomeProperty: {}", userHomeProperty);
		
		File userHomeFile = new File(userHomeProperty);
		LOGGER.trace("userHomeFile: {}", userHomeFile);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");

		// Show the file chooser dialog
        Stage stage = (Stage) importDataButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        // Update the status label
        if (selectedFile != null) {
        	LOGGER.info("File selected: {}", selectedFile.getAbsolutePath());
        	
        	// TODO Delegate this data to handle CSV Parsing
        } else {
        	LOGGER.info("No file selected");
        }
	}
	
}
