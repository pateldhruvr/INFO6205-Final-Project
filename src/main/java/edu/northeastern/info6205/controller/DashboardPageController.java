package edu.northeastern.info6205.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.info6205.model.Point;
import edu.northeastern.info6205.service.CSVParserService;
import edu.northeastern.info6205.service.MercatorProjectionService;
import edu.northeastern.info6205.service.impl.CSVParserServiceImpl;
import edu.northeastern.info6205.service.impl.MercatorProjectionServiceImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DashboardPageController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardPageController.class);
	
	@FXML
	private Button importDataButton;
	
	@FXML
	private Pane graphArea;
	
//	private MapView mapView;
	
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
		
//		importDataButton.setOnMouseClicked(event -> handleImportFileMenuClick());
//		importDataButton.setOnMouseClicked(event -> dummyGoogle());
		importDataButton.setOnMouseClicked(event -> drawCircles());

		LOGGER.trace("import Data click listener has been initialized");
	}
	
	private void drawCircles() {
		double screenWidth = graphArea.getWidth();
    	double screenHeight = graphArea.getHeight();
    	
		ObservableList<Node> children = graphArea.getChildren();
    	children.clear();
    	
		double circleRadius = 5.0;
		
    	Circle circle1 = new Circle();
    	circle1.setRadius(circleRadius);
		circle1.setFill(Color.BLUE);
		circle1.setCenterX(0);
		circle1.setCenterY(0);
		children.add(circle1);
		
		Circle circle2 = new Circle();
		circle2.setRadius(circleRadius);
		circle2.setFill(Color.RED);
		circle2.setCenterX(screenWidth);
		circle2.setCenterY(0);
		children.add(circle2);
		
		Circle circle3 = new Circle();
		circle3.setRadius(circleRadius);
		circle3.setFill(Color.YELLOW);
		circle3.setCenterX(screenWidth);
		circle3.setCenterY(screenHeight);
		children.add(circle3);
		
		Circle circle4 = new Circle();
		circle4.setRadius(circleRadius);
		circle4.setFill(Color.BLACK);
		circle4.setCenterX(0);
		circle4.setCenterY(screenHeight);
		children.add(circle4);
	}

	private void handleImportFileMenuClick() {
		LOGGER.trace("import file menu button has been clicked");
		
		String userHomeProperty = System.getProperty("user.home");
		LOGGER.trace("userHomeProperty: {}", userHomeProperty);
		
		File userHomeFile = new File(userHomeProperty);
		LOGGER.trace("userHomeFile: {}", userHomeFile);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");

        Stage stage = (Stage) importDataButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        if (selectedFile == null) {
        	LOGGER.debug("No file selected, handleImportFileMenuClick() will return");
        	return;
        }
        
        LOGGER.debug("File selected: {}", selectedFile.getAbsolutePath());
    	
    	CSVParserService csvParserService = CSVParserServiceImpl.getInstance();
    	List<Point> points = csvParserService.parsePoints(selectedFile);
    	LOGGER.debug("Will add points with size: {}", points.size());
    	
    	double screenWidth = graphArea.getWidth();
    	double screenHeight = graphArea.getHeight();
    	
    	MercatorProjectionService projectionService = MercatorProjectionServiceImpl.getInstance();
    	points = projectionService.calculateProjections(points, screenWidth, screenHeight);
    	
    	double circleRadius = 5.0;
    	Paint circleFill = Color.BLUE;

    	ObservableList<Node> children = graphArea.getChildren();
    	children.clear();
    	
    	for (Point point : points) {
    		Circle circle = new Circle();
    		
    		circle.setId(point.getId());
    		circle.setRadius(circleRadius);
    		circle.setFill(circleFill);
    		circle.setCenterX(point.getX());
    		circle.setCenterY(point.getY());
    		
    		children.add(circle);
    	}
    	
	}
	
}
