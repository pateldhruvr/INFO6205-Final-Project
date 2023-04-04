package edu.northeastern.info6205.service;

import java.io.File;
import java.util.List;

import edu.northeastern.info6205.model.Point;

/**
 * Service to parse the CSV Data and save
 * 
 * */
public interface CSVParserService {

	/**
	 * Parses the given File (which is expected in CSV Format)
	 * and returns list of {@link Point}
	 * */
	List<Point> parsePoints(File file);
	
}
