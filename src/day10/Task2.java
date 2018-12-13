package day10;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Task2 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day10", "input.txt"));

        List<Point> points = new ArrayList<>();
        for (String point : input) {
            Point p = new Point(point);
            points.add(p);
        }

        int seconds = getSeconds(points);
        System.out.println(seconds);
    }

    private static int getSeconds(List<Point> points) {
        int minX = Integer.MIN_VALUE;
        int maxX = Integer.MAX_VALUE;
        int minY = Integer.MIN_VALUE;
        int maxY = Integer.MAX_VALUE;
        int yDiff = Integer.MAX_VALUE;

        int seconds = 0;
        boolean first = true;
        do {
            if (first) {
                first = false;
            } else {
                yDiff = maxY - minY;
            }
            minX = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            minY = Integer.MAX_VALUE;
            maxY = Integer.MIN_VALUE;
            for (Point point : points) {
                point.travelForwards();
                minX = Math.min(minX, point.x);
                maxX = Math.max(maxX, point.x);
                minY = Math.min(minY, point.y);
                maxY = Math.max(maxY, point.y);
            }
            seconds++;

        } while (maxY - minY < yDiff);

        for (Point point : points) {
            minX = Math.min(minX, point.x);
            maxX = Math.max(maxX, point.x);
            minY = Math.min(minY, point.y);
            maxY = Math.max(maxY, point.y);
            point.travelBackwards();
        }
        seconds--;
        return seconds;
    }

    private static class Point {

        private int x;
        private int y;
        private int xVelocity;
        private int yVelocity;

        public Point(String point) {
            x = Integer.valueOf(point.substring(point.indexOf('<') + 1, point.indexOf(",")).trim());
            y = Integer.valueOf(point.substring(point.indexOf(',') + 1, point.indexOf(">")).trim());
            point = point.substring(point.indexOf("velocity"));
            xVelocity = Integer.valueOf(point.substring(point.indexOf('<') + 1, point.indexOf(",")).trim());
            yVelocity = Integer.valueOf(point.substring(point.indexOf(',') + 1, point.indexOf(">")).trim());
        }

        public void travelForwards() {
            x += xVelocity;
            y += yVelocity;
        }

        public void travelBackwards() {
            x -= xVelocity;
            y -= yVelocity;
        }

    }

}
