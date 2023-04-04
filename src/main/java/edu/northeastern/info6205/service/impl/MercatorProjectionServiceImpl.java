package edu.northeastern.info6205.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.info6205.model.Point;
import edu.northeastern.info6205.service.MercatorProjectionService;

public class MercatorProjectionServiceImpl implements MercatorProjectionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MercatorProjectionServiceImpl.class);
	
	private static MercatorProjectionService instance;
	
	private MercatorProjectionServiceImpl() {
		LOGGER.debug("Initiliasing the service");
	}
	
	public static MercatorProjectionService getInstance() {
		if (instance != null) {
			LOGGER.trace("instance alreaady created will return it");
			return instance;
		}
		
		LOGGER.debug("creating the first instance");
		instance = new MercatorProjectionServiceImpl();
		return instance;
	}

	@Override
	public List<Point> calculateProjections(
			List<Point> points, 
			double screenWidth, 
			double screenHeight) {
		LOGGER.trace(
				"calculate projections for points size: {}, screenWidth: {}, screenHeight: {}", 
				points.size(),
				screenWidth,
				screenHeight);

		final double EARTH_RADIUS = 6371.0;
		
		// Find the minimum and maximum values of latitude and longitude
		for (Point point : points) {
			double longitudeRadians = Math.toRadians(point.getLongitude());
	        double latitudeRadians = Math.toRadians(point.getLatitude());

	        double x = longitudeRadians * EARTH_RADIUS;
	        double y = Math.log(Math.tan(latitudeRadians / 2 + Math.PI / 4)) * EARTH_RADIUS;

	        // Scale and translate to fit on the map
	        double scaleX = screenWidth / (Math.PI * 2 * EARTH_RADIUS);
	        double scaleY = -scaleX;
	        double translateX = screenWidth / 2;
	        double translateY = screenHeight / 2;

	        point.setX(x * scaleX + translateX);
	        point.setY(y * scaleY + translateY);
		}
		
		LOGGER.trace("Finished calculating projections");
		return points;
	}
}
