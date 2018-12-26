package day12;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Task1 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();

        String initialState = null;
        Set<Combination> combinations = new HashSet<>();

        try (Scanner sc = new Scanner(Paths.get(absolutePath, "src/day12", "input.txt"))) {
            initialState = sc.nextLine();
            initialState = initialState.substring(initialState.lastIndexOf(" ") + 1);
            sc.nextLine();
            while (sc.hasNextLine()) {
                Combination c = new Combination(sc.nextLine());
                combinations.add(c);
            }
        }

        int leftMostPotPosition = 0;
        String state = initialState;
        int numberOfGenerations = 20;
        for (int i = 1; i <= numberOfGenerations; i++) {
            // Prepend infinite empty pots on both the left and right side
            // Infinitely in this case = 5 since that's the maximum amount needed to
            // (possibly) generate a new flower in an empty pot
            state = "....." + state + ".....";
            state = generation(state, combinations);

            leftMostPotPosition = getLeftMostPotPosition(state, leftMostPotPosition);

            // Strip infinite empty pots
            state = state.substring(state.indexOf('#'), state.lastIndexOf('#') + 1);
        }
        System.out.println(getSum(state, leftMostPotPosition));
    }

    private static String generation(String state, Set<Combination> combinations) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < state.length(); i++) {
            String subState = getSubState(state, i);
            boolean matchFound = false;
            for (Combination c : combinations) {
                if (c.matches(subState)) {
                    builder.append(c.produces);
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                builder.append(state.charAt(i));
            }
        }
        return builder.toString();
    }

    private static int getLeftMostPotPosition(String state, int leftMostPotPosition) {
        String potsToTheLeft = state.substring(0, state.indexOf('#'));
        return potsToTheLeft.length() - 5 + leftMostPotPosition;
    }

    private static String getSubState(String state, int characterPosition) {
        int beginningPos = Math.max(0, characterPosition - 2);
        int endingPos = Math.min(state.length() - 1, characterPosition + 2);
        return state.substring(beginningPos, endingPos + 1);
    }

    private static int getSum(String state, int leftMostPotPosition) {
        int sum = 0;
        for (int i = leftMostPotPosition; i < state.length() + leftMostPotPosition; i++) {
            char c = state.charAt(i - leftMostPotPosition);
            if (c == '#') {
                sum += i;
            }
        }
        return sum;
    }

    private static class Combination {

        private String combinationRaw;

        private char produces;

        public Combination(String combinationRaw) {
            this.combinationRaw = combinationRaw.substring(0, combinationRaw.indexOf(" "));
            this.produces = combinationRaw.charAt(combinationRaw.length() - 1);
        }

        public boolean matches(String combination) {
            if (combinationRaw.length() != combination.length()) {
                return false;
            }
            for (int i = 0; i < combination.length(); i++) {
                if (!Objects.equals(combinationRaw.charAt(i), combination.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

    }

}
