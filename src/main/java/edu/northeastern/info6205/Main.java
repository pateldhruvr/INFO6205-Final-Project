package edu.northeastern.info6205;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		LOGGER.info("Launching Main with args: {}", Arrays.asList(args));
		App.main(args);
	}

}
