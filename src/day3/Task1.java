package day3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Task1 {

    private static int fabricXSize = 1000;
    private static int fabricYSize = 1000;

    private static boolean[][] alreadyTakenFabric = new boolean[fabricXSize][fabricYSize];
    private static boolean[][] alreadyVisitedSquareInch = new boolean[fabricXSize][fabricYSize];
    
    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day3", "input.txt"));
        System.out.println(squareInchesWithinTwoOrMoreClaims(input));
    }

    private static int squareInchesWithinTwoOrMoreClaims(List<String> input) {
        int alreadyTakenSquareInches = 0;
        for (String claim : input) {
            Claim c = new Claim(claim);
            for (int k = c.xStart; k < c.xStart + c.xSize; k++) {
                for (int l = c.yStart; l < c.yStart + c.ySize; l++) {
                    if (!alreadyTakenFabric[k][l]) {
                        alreadyTakenFabric[k][l] = true;
                    } else if (alreadyTakenFabric[k][l] && !alreadyVisitedSquareInch[k][l]) {
                        alreadyVisitedSquareInch[k][l] = true;
                        alreadyTakenSquareInches++;
                    }
                }
            }
        }
        return alreadyTakenSquareInches;
    }
    
    private static class Claim {
        private int xStart;
        private int yStart;
        private int xSize;
        private int ySize;
        
        public Claim(String claim) {
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
