package edu.northeastern.info6205.tspsolver.harshil;

import edu.northeastern.info6205.tspsolver.model.Point;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void writePoints(List<Point> points){
        //TODO: update the path and make it generic
        String filePath = "//Users/dhruvpatel/CS/PSA/TSP-Final_Project/src/main/resources/data/ImprovedPointsCsv.csv";

        String headerRow = "ID,Latitude,Longitude";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write(headerRow);
            writer.newLine();
            for (Point point: points) {
                String id = point.getId();
                double latitude = point.getLatitude();
                double longitude = point.getLongitude();

                writer.write(id);
                writer.write(",");
                writer.write(String.valueOf(latitude));
                writer.write(",");
                writer.write(String.valueOf(longitude));
                writer.newLine();
            }
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing data to file: " + e.getMessage());
        }
    }
}
