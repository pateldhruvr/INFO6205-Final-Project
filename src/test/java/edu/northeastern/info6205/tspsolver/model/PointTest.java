package edu.northeastern.info6205.tspsolver.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.northeastern.info6205.tspsolver.constant.Constant;

public class PointTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PointTest.class);

    @Test
    public void invalidCoordinates() {
        LOGGER.trace("invalidCoordinates()");

        assertThrows(IllegalArgumentException.class, () -> {
            new Point(Constant.BLANK_STRING, 100.0, 0.0);
            new Point(Constant.BLANK_STRING, 40.712776, 40.0);
        });
    }

    @Test
    public void invalidCoordinates2() {
        LOGGER.trace("invalidCoordinates2()");

        assertThrows(IllegalArgumentException.class, () -> {
            new Point(Constant.BLANK_STRING, 0.0, 181.0);
            new Point(Constant.BLANK_STRING, 1.0, 40.0);
        });
    }

    @Test
    public void invalidCoordinates3() {
        LOGGER.trace("invalidCoordinates3()");

        assertThrows(IllegalArgumentException.class, () -> {
            new Point(Constant.BLANK_STRING, 0.0, 18.0);
            new Point(Constant.BLANK_STRING, -91.0, 40.0);
        });
    }

    @Test
    public void invalidCoordinates4() {
        LOGGER.trace("invalidCoordinates4()");

        assertThrows(IllegalArgumentException.class, () -> {
            new Point(Constant.BLANK_STRING, 0.0, 18.0);
            new Point(Constant.BLANK_STRING, -9.0, -181.0);
        });
    }

    @Test
    public void sameCoordinates() {
        LOGGER.trace("sameCoordinates()");
        Point point1 = new Point(Constant.APP_NAME, 1.0, 18.0);
        Point point2 = new Point(Constant.APP_NAME, 1.0, 18.0);
        assertEquals(point1, point2);
    }

    @Test
    public void sameCoordinatesDifferentId() {
        LOGGER.trace("sameCoordinates2()");
        Point point1 = new Point(Constant.APP_NAME, 1.0, 18.0);
        Point point2 = new Point(Constant.DASH, 1.0, 18.0);
        assertNotEquals(point1, point2);
    }

}
