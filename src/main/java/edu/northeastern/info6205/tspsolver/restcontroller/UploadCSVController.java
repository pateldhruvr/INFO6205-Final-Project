package edu.northeastern.info6205.tspsolver.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.northeastern.info6205.tspsolver.model.Point;
import edu.northeastern.info6205.tspsolver.service.CSVParserService;
import edu.northeastern.info6205.tspsolver.service.TestService;

@RestController
public class UploadCSVController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadCSVController.class);

	@Autowired
	private CSVParserService csvService;
	
	@Autowired
	private TestService testService;
	
	@PostMapping("/api/csv")
	public String uploadCSV(@RequestParam MultipartFile multiPartFile) {
		LOGGER.debug("Starting to upload the CSV File");
		List<Point> points = csvService.parsePoints(multiPartFile);
		testService.testAsync(points);
		return "OK";
	}
	
}