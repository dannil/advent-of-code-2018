package day6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Objects;

public class Task2 {

    private static int[][] grid;

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day6", "input.txt"));

        int indexRepresentingRegion = 1;
        int largestX = -1;
        int largestY = -1;
        
        Map<Point, Integer> points = new HashMap<>();
        for (String coordinate : input) {
            Point point = new Point(coordinate);
            points.put(point, indexRepresentingRegion);
            indexRepresentingRegion++;
            largestX = Math.max(largestX, point.x);
            largestY = Math.max(largestY, point.y);
        }
        grid = new int[largestX + 1][largestY + 1];

        for (Point point : points.keySet()) {
            grid[point.x][point.y] = points.get(point);
        }

        populateAreaFrequencies(points);
        
        System.out.println(getRegionSize(points, 10000));
    }

    private static void populateAreaFrequencies(Map<Point, Integer> points) {
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid[1].length; y++) {
                Point tempPoint = new Point(x, y);
                if (!points.keySet().contains(tempPoint)) {
                    Point closestPoint = null;
                    Map<Point, Integer> distancesToPoints = new HashMap<>();
                    int shortestDistance = Integer.MAX_VALUE;
                    for (Point point : points.keySet()) {
                        int distance = point.getManhattanDistance(tempPoint);
                        distancesToPoints.put(point, distance);
                        shortestDistance = Math.min(shortestDistance, distance);
                    }
                    int numberOfShortestDistances = 0;
                    for (Entry<Point, Integer> entry : distancesToPoints.entrySet()) {
                        if (entry.getValue() == shortestDistance) {
                            numberOfShortestDistances++;
                            closestPoint = entry.getKey();
                        }
                    }
                    if (numberOfShortestDistances == 1) {
                        grid[x][y] = points.get(closestPoint);
                    }
                }
            }
        }
    }
    
    private static int getRegionSize(Map<Point, Integer> points, int totalDistance) {
        int regionSize = 0;
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid[1].length; y++) {
                int distanceForCoordinateToAllPointsSummed = 0;
                Point tempPoint = new Point(x, y);
                for (Point point : points.keySet()) {
                    int distance = point.getManhattanDistance(tempPoint);
                    distanceForCoordinateToAllPointsSummed += distance;
                }
                if (distanceForCoordinateToAllPointsSummed < totalDistance) {
                    regionSize++;
                }
            }
        }
        return regionSize;
    }

    private static class Point {
        private int x;
        private int y;

        public Point(String coordinates) {
            x = Integer.valueOf(coordinates.substring(0, coordinates.indexOf(",")));
            y = Integer.valueOf(coordinates.substring(coordinates.indexOf(" ") + 1));
        }
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getManhattanDistance(Point p) {
            return Math.abs(x - p.x) + Math.abs(y - p.y);
        }

        @Override
        public boolean equals(Object o) {
            Point p = (Point) o;
            return Objects.equals(this.x, p.x) && Objects.equals(this.y, p.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
        
    }

}

