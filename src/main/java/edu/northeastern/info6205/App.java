package edu.northeastern.info6205;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.info6205.constant.Constant;
import edu.northeastern.info6205.service.ErrorHandlingService;
import edu.northeastern.info6205.service.impl.ErrorHandlingServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static ErrorHandlingService errorHandlingService;
	
    @Override
    public void start(Stage stage) {
		LOGGER.info("starting the stage: {}", stage);

		try {
			Thread.setDefaultUncaughtExceptionHandler(App::showError);
			
			String javaVersion = SystemInfo.javaVersion();
			String javafxVersion = SystemInfo.javafxVersion();
			LOGGER.info("javaVersion: {}, javafxVersion: {}", javaVersion, javafxVersion);
			
			String configKey = "log4j.configurationFile";
	        LOGGER.info("configKey: {}", configKey);
	        
	        String configProperty = System.getProperty(configKey);
	        LOGGER.info("configProperty: {}", configProperty);
	        
	        final String FILE_NAME = "/pages/DashboardPage.fxml";
//	        final String FILE_NAME = "/GraphTest.fxml";
	        LOGGER.info("FILE_NAME: {}", FILE_NAME);
	        
	        Class<?> clazz = getClass();
	        LOGGER.trace("clazz: {}", clazz);
	        
			URL url = clazz.getResource(FILE_NAME);
	        LOGGER.trace("url: {}", url);

			Parent root = FXMLLoader.load(url);
			
			/*
			final double height = 720;
			final double width = 720;
			Scene scene = new Scene(root, height, width);
			*/
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add("stylesheets/application.css");
			
			stage.setTitle(Constant.APP_NAME);
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.setResizable(false);
			stage.show();
			
			errorHandlingService = ErrorHandlingServiceImpl.getInstance();
		} catch (Exception e) {
			LOGGER.error("Exception in start()", e.getMessage(), e);
			System.exit(1);
		}
    }

    @Override
	public void stop() throws Exception {
		LOGGER.info("Application has stopped");
		super.stop();
	}
    
    private static void showError(Thread t, Throwable e) {
		LOGGER.error("Something went wrong: {}", e.getMessage(), e);
		
		/*
		boolean isFXApplicationThread = Platform.isFxApplicationThread();
		LOGGER.debug("isFXApplicationThread: {}", isFXApplicationThread);
		
		if (isFXApplicationThread) {
			showErrorDialog(e);
		}
		*/
		
		errorHandlingService.handleError(e);
	}
    
    public static void main(String[] args) {
        launch();
    }

}