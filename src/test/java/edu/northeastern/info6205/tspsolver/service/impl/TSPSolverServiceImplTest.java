package edu.northeastern.info6205.tspsolver.service.impl;

import edu.northeastern.info6205.tspsolver.model.Point;
import edu.northeastern.info6205.tspsolver.model.TSPPayload;
import edu.northeastern.info6205.tspsolver.service.TSPSolverService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

import java.util.Arrays;
import java.util.List;

public class TSPSolverServiceImplTest {
    @Test
    @Disabled("This test case needs to be implemented")
    public void test() {

    }

    @Test
    public void testSolve() {
        List<Point> points = Arrays.asList(
                new Point("1", 0.0, 0.0),
                new Point("2", 1.0, 1.0),
                new Point("3", 2.0, 2.0),
                new Point("4", 3.0, 3.0),
                new Point("5", 4.0, 4.0),
                new Point("6", 5.0, 5.0)
        );

        int startingPointIndex = 0;
        TSPPayload payload = new TSPPayload();

        TSPSolverService tspSolverService = TSPSolverServiceImpl.getInstance();
        List<Point> result = tspSolverService.solve(points, startingPointIndex, payload);

        List<Point> expected = Arrays.asList(
                new Point("1", 0.0, 0.0),
                new Point("2", 1.0, 1.0),
                new Point("3", 2.0, 2.0),
                new Point("4", 3.0, 3.0),
                new Point("5", 4.0, 4.0),
                new Point("6", 5.0, 5.0)
        );

        Assertions.assertEquals(expected, result);
    }

}
