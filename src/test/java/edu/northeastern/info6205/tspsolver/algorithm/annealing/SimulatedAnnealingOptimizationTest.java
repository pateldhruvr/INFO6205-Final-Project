package edu.northeastern.info6205.tspsolver.algorithm.annealing;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import edu.northeastern.info6205.tspsolver.algorithm.christofides.Christofides;
import edu.northeastern.info6205.tspsolver.algorithm.mst.PrimsMST;
import edu.northeastern.info6205.tspsolver.constant.Constant;
import edu.northeastern.info6205.tspsolver.model.Point;
import edu.northeastern.info6205.tspsolver.model.TSPPayload.SimulatedAnnealingPayload;
import edu.northeastern.info6205.tspsolver.service.CSVParserService;
import edu.northeastern.info6205.tspsolver.service.impl.CSVParserServiceImpl;
import edu.northeastern.info6205.tspsolver.util.HaversineDistanceUtil;
import edu.northeastern.info6205.tspsolver.util.PointUtil;

public class SimulatedAnnealingOptimizationTest {
    
	@Test
    public void christofidesTourNullTest() {
		assertThrows(NullPointerException.class, () -> {
			SimulatedAnnealingOptimization optimization = new SimulatedAnnealingOptimization(
	    			null, 
	    			new double[][] {{1}}, 
	    			0, 
	    			0,
	    			0,
	    			0);
			
			optimization.solve();
		});
    }
	
	@Test
    public void distanceMatrixNullTest() {
    	assertThrows(NullPointerException.class, () -> {
    		new SimulatedAnnealingOptimization(
    				new int[] {1}, 
        			null, 
        			0, 
        			0,
        			0,
        			0);
    	});
    }
	
	@Test
    public void graphAndChristofidesTourNullTest() {
    	assertThrows(NullPointerException.class, () -> {
    		new SimulatedAnnealingOptimization(
    				null, 
        			null, 
        			0, 
        			0,
        			0,
        			0);
    	});
    }
	
	@Test
	public void runSmallOptimizationTest() {
		test(Constant.TEST_DATA_FILE_SMALL);
	}

	@Test
	public void runBigOptimizationTest() {
		test(Constant.TEST_DATA_FILE_BIG);

	}
	
	private void test(String fileName) {
		CSVParserService csvParserService = CSVParserServiceImpl.getInstance();
    	List<Point> points = csvParserService.parsePoints(fileName);
    	
    	Christofides christofides = new Christofides(points);
		List<Point> tour = christofides.solve();
		
		// Last point and first point are same in Christofides tour
		tour.remove(tour.size() - 1);

		int[] christofidesTour = tour.stream()
				.mapToInt(p -> Integer.parseInt(p.getId()))
				.toArray();
		
		int n = points.size();
		double[][] graph = new double[n][n];
		for (int i = 0; i < n; i++) {
			Point source = points.get(i);
			for (int j = i + 1; j < n; j++) {
				Point destination = points.get(j);
				double distance = HaversineDistanceUtil.haversine(destination, source);
				graph[i][j] = distance;
				graph[j][i] = distance;
			}
		}
		
		SimulatedAnnealingPayload annealingPayload = new SimulatedAnnealingPayload();
		annealingPayload.setMaxIteration(1000000);
		annealingPayload.setStartingTemperature(1000);
		annealingPayload.setFinalTemperature(1);
		annealingPayload.setCoolingRate(0.9995);
		
		SimulatedAnnealingOptimization optimization = new SimulatedAnnealingOptimization(
				christofidesTour, 
				graph, 
				annealingPayload.getMaxIteration(), 
				annealingPayload.getStartingTemperature(), 
				annealingPayload.getFinalTemperature(), 
				annealingPayload.getCoolingRate());
		
		int[] path = optimization.solve();
		
		Map<Integer, Point> pointMap = new HashMap<>();
		for (Point point : points) {
			pointMap.put(Integer.parseInt(point.getId()), point);
		}
		
		List<Point> tspTour = new ArrayList<>();
		for (int node : path) {
			tspTour.add(pointMap.get(node));
		}
		
		int startingPointIndex = 0;
		Point firstPoint = pointMap.get(startingPointIndex);
		int firstPointIndex = tspTour.indexOf(firstPoint);
		
		if (firstPointIndex != -1) {
			int rotations = firstPointIndex;
			Collections.rotate(tspTour, -rotations);
		}

		tspTour.add(tspTour.get(0));
		
		double tspTourCost = PointUtil.getTotalCost(tspTour);
		
		PrimsMST primsMst = new PrimsMST(points);
        double mstCost = primsMst.getMstCost();
        
        double percentage = ((tspTourCost - mstCost)/mstCost) * 100;
        assertTrue(percentage < 100);
	}
}
