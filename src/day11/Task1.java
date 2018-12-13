package day11;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        int gridSerialNumber = Integer.MIN_VALUE;
        try (Scanner sc = new Scanner(Paths.get(absolutePath, "src/day11", "input.txt"))) {
            gridSerialNumber = sc.nextInt();
        }
        int[][] powerLevels = buildPowerLevelsArray(gridSerialNumber);
        String coordinates = getTopLeftCoordinate(powerLevels, 3);
        System.out.println(coordinates);
    }

    public static int[][] buildPowerLevelsArray(int gridSerialNumber) {
        int[][] powerLevels = new int[300][300];
        for (int x = 1; x <= powerLevels.length; x++) {
            for (int y = 1; y <= powerLevels[0].length; y++) {
                int rackId = x + 10;
                int powerLevel = rackId * y;
                powerLevel += gridSerialNumber;
                powerLevel *= rackId;
                int hundrethDigit = (powerLevel / 100) % 10;
                powerLevels[x - 1][y - 1] = hundrethDigit - 5;
            }
        }
        return powerLevels;
    }

    public static String getTopLeftCoordinate(int[][] powerLevels, int size) {
        String topLeftCoordinate = null;
        int maximumSubArraySum = 0;
        for (int x = 0; x < powerLevels.length - size; x++) {
            for (int y = 0; y < powerLevels[0].length - size; y++) {
                int subArraySum = getSubArraySum(powerLevels, x, y, size);
                if (subArraySum > maximumSubArraySum) {
                    maximumSubArraySum = subArraySum;
                    topLeftCoordinate = (x + 1) + "," + (y + 1);
                }
            }
        }
        return topLeftCoordinate;
    }

    public static int getSubArraySum(int[][] subArray, int topLeftX, int topLeftY, int size) {
        int sum = 0;
        for (int x = topLeftX; x < topLeftX + size; x++) {
            for (int y = topLeftY; y < topLeftY + size; y++) {
                sum += subArray[x][y];
            }
        }
        return sum;
    }

}
