package edu.northeastern.info6205.service;

import java.util.List;

import edu.northeastern.info6205.model.Point;

/**
 * Using Mercator Projection to allow
 * plotting points
 * */
public interface MercatorProjectionService {

	/**
	 * Will set the x and y position of the points
	 * according to their real world coordinates
	 * using the mercator projection
	 * */
	List<Point> calculateProjections(
			List<Point> points,
			double screenWidth,
			double screenHeight);
	
}
