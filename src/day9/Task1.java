package day9;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class Task1 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day9", "input.txt"));

        String[] parts = input.get(0).split("\\D+");
        int numberOfPlayers = Integer.valueOf(parts[0]);
        int lastMarbleWorth = Integer.valueOf(parts[1]);

        System.out.println(marble(numberOfPlayers, lastMarbleWorth));
    }

    private static long marble(int numberOfPlayers, int lastMarbleWorth) {
        long[] playerScores = new long[numberOfPlayers];
        RotatingArrayDeque<Integer> marbles = new RotatingArrayDeque<>();
        marbles.add(0);
        for (int i = 1; i <= lastMarbleWorth; i++) {
            int currentPlayer = i % numberOfPlayers + 1;
            if (i % 23 == 0) {
                marbles.rotate(-7);
                playerScores[currentPlayer - 1] += i + marbles.pop();
            } else {
                marbles.rotate(2);
                marbles.addLast(i);
            }
        }
        return Arrays.stream(playerScores).max().getAsLong();
    }

    private static class RotatingArrayDeque<T> extends ArrayDeque<T> {

        private static final long serialVersionUID = 2804714465246489308L;

        public void rotate(int steps) {
            if (steps > 0) {
                for (int i = 0; i < steps; i++) {
                    T t = removeLast();
                    addFirst(t);
                }
            } else {
                for (int i = 0; i < Math.abs(steps) - 1; i++) {
                    T t = removeFirst();
                    addLast(t);
                }
            }
        }

    }

}
