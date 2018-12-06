package day6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class Task1 {

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

        Set<Integer> infiniteAreas = getInfiniteAreas(grid);
        Map<Integer, Integer> areaFrequencies = getAreaFrequencies(grid);
        areaFrequencies.keySet().removeAll(infiniteAreas);
        int largestNonInfiniteArea = areaFrequencies.values().stream().mapToInt(x -> x).max().getAsInt();
        System.out.println(largestNonInfiniteArea);
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

    private static Set<Integer> getInfiniteAreas(int[][] grid) {
        Set<Integer> infinite = new HashSet<>();
        for (int i = 0; i < grid[1].length; i++) {
            infinite.add(grid[0][i]);
        }
        for (int i = 0; i < grid[1].length; i++) {
            infinite.add(grid[i][grid[1].length - 1]);
        }
        for (int i = 0; i < grid[0].length; i++) {
            infinite.add(grid[i][0]);
        }
        for (int i = 0; i < grid[0].length; i++) {
            infinite.add(grid[grid[0].length - 1][i]);
        }
        return infinite;
    }

    private static Map<Integer, Integer> getAreaFrequencies(int[][] grid) {
        Map<Integer, Integer> areaFrequencies = new HashMap<>();
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid[1].length; y++) {
                int coordinate = grid[x][y];
                if (areaFrequencies.containsKey(coordinate)) {
                    areaFrequencies.put(coordinate, areaFrequencies.get(coordinate) + 1);
                } else {
                    areaFrequencies.put(coordinate, 1);
                }
            }
        }
        return areaFrequencies;
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
