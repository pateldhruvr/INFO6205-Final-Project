package edu.northeastern.info6205.test;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Just created a class to test if
 * maven exec class is working for any main program
 * along with custom logger configurations locations
 * */
public class Test {
	private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {
		LOGGER.info("Running Test class with args: {}", Arrays.asList(args));
		
		// Just checking if error stack trace is printing
		try {
			int x = 1 / 0;
			
			/* 
			 * :) No use printing this, as above line will generate exception
			 * 
			 * But still printing in logs to prevent warning in eclipse IDE 
			 * */
			LOGGER.info("x: {}", x);
		} catch (Exception e) {
			LOGGER.error("Exception in main(): {}", e.getMessage(), e);
		}
		
		System.exit(0);
	}
	
}
