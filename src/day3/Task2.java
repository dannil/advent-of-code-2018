package day3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Task2 {

    private static int fabricXSize = 1000;
    private static int fabricYSize = 1000;

    private static boolean[][] alreadyTakenFabric = new boolean[fabricXSize][fabricYSize];
    private static boolean[][] alreadyVisitedSquareInch = new boolean[fabricXSize][fabricYSize];

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day3", "input.txt"));
        buildFabricArray(input);
        System.out.println(nonOverlappingId(input));
    }

    private static void buildFabricArray(List<String> input) {
        for (String claim : input) {
            Claim c = new Claim(claim);
            for (int k = c.xStart; k < c.xStart + c.xSize; k++) {
                for (int l = c.yStart; l < c.yStart + c.ySize; l++) {
                    if (!alreadyTakenFabric[k][l]) {
                        alreadyTakenFabric[k][l] = true;
                    } else if (alreadyTakenFabric[k][l] && !alreadyVisitedSquareInch[k][l]) {
                        alreadyVisitedSquareInch[k][l] = true;
                    }
                }
            }
        }
    }
    
    private static int nonOverlappingId(List<String> input) {
        for (String claim : input) {
            Claim c = new Claim(claim);
            boolean isNonOverlapping = true;
            for (int k = c.xStart; k < c.xStart + c.xSize; k++) {
                for (int l = c.yStart; l < c.yStart + c.ySize; l++) {
                    if (alreadyVisitedSquareInch[k][l]) {
                        isNonOverlapping = false;
                    }
                }
            }
            if (isNonOverlapping) {
                return c.id;
            }
        }
        return -1;
    }
    
    private static class Claim {
        private int id;
        private int xStart;
        private int yStart;
        private int xSize;
        private int ySize;
        
        public Claim(String claim) {
            id = Integer.valueOf(claim.substring(1, claim.indexOf(" ")));
            
            int locationOfAt = claim.indexOf("@");
            claim = claim.substring(locationOfAt + 2);

            int locationOfComma = claim.indexOf(",");
            int locationOfColon = claim.indexOf(":");
            xStart = Integer.valueOf(claim.substring(0, locationOfComma));
            yStart = Integer.valueOf(claim.substring(locationOfComma + 1, locationOfColon));

            claim = claim.substring(locationOfColon + 2);

            int locationOfDimension = claim.indexOf("x");
            xSize = Integer.valueOf(claim.substring(0, locationOfDimension));
            ySize = Integer.valueOf(claim.substring(locationOfDimension + 1));
        }
        
    }

}
