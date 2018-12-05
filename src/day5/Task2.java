package day5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Task2 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day5", "input.txt"));
        String polymer = input.get(0);
        System.out.println(findShortestPolymerLength(polymer));
    }

    private static int findShortestPolymerLength(String polymer) {
        int shortestPolymerLength = Integer.MAX_VALUE;
        for (char c = 'a'; c <= 'z'; c++) {
            char cUpper = Character.toUpperCase(c);
            String polymerCopy = new String(polymer).replaceAll("[" + c + cUpper + "]", "");
            shortestPolymerLength = Math.min(shortestPolymerLength, react(polymerCopy));
        }
        return shortestPolymerLength;
    }

    private static int react(String polymer) {
        int reactions = 0;
        StringBuilder builder = new StringBuilder();
        int i;
        for (i = 0; i < polymer.length() - 1; i++) {
            char currentUnit = polymer.charAt(i);
            char nextUnit = polymer.charAt(i + 1);
            char currentUnitUpper = Character.toUpperCase(currentUnit);
            char nextUnitUpper = Character.toUpperCase(nextUnit);
            if (Objects.equals(currentUnitUpper, nextUnitUpper) && isCasingDifferent(currentUnit, nextUnit)) {
                i++;
                reactions++;
            } else {
                builder.append(currentUnit);
            }
        }
        if (i == polymer.length() - 1) {
            builder.append(polymer.charAt(polymer.length() - 1));
        }
        if (reactions == 0) {
            return builder.length();
        }
        return react(builder.toString());
    }

    private static boolean isCasingDifferent(char c1, char c2) {
        return (Character.isUpperCase(c1) && Character.isLowerCase(c2)
                || Character.isLowerCase(c1) && Character.isUpperCase(c2));
    }

}
