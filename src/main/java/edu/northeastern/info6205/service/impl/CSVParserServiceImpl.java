package edu.northeastern.info6205.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.info6205.constant.Constant;
import edu.northeastern.info6205.model.Point;
import edu.northeastern.info6205.service.CSVParserService;
import edu.northeastern.info6205.service.ErrorHandlingService;

public class CSVParserServiceImpl implements CSVParserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CSVParserServiceImpl.class);

	private static CSVParserService instance;
	
	private ErrorHandlingService errorHandlingService;
	
	private CSVParserServiceImpl() {
		LOGGER.debug("Initiliasing the service");
		
		errorHandlingService = ErrorHandlingServiceImpl.getInstance();
	}
	
	public static CSVParserService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new CSVParserServiceImpl();
		return instance;
	}

	@Override
	public List<Point> parsePoints(File file) {
		LOGGER.trace("parsing points in file: {}", file);
		
		List<Point> points = new ArrayList<>();
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line = bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split(Constant.COMMA);
				if (parts[6].equals(Constant.NO_LOCATION)) {
					continue;
				}

				String id = parts[0];
				double longitude = Double.parseDouble(parts[4]);
				double latitude = Double.parseDouble(parts[5]);
				
				Point node = new Point(id, latitude, longitude);
				points.add(node);
			}
			
			
		} catch (Exception  e) {
			LOGGER.error("Error in parseNodes(): {}", e.getMessage(), e);
			errorHandlingService.handleError(e);
		}
		
		LOGGER.trace("parsed points size: {} from file: {}", points.size(), file);
		return points;
	}
	
}
