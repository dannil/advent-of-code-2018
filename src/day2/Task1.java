package day2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task1 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day2", "input.txt"));
        
        int numberOfTwoLetters = 0;
        int numberOfThreeLetters = 0;
        for (String boxId : input) {
            Box b = new Box(boxId);
            if (b.hasOccurances(2)) {
                numberOfTwoLetters++;
            }
            if (b.hasOccurances(3)) {
                numberOfThreeLetters++;
            }
        }
        System.out.println(numberOfTwoLetters * numberOfThreeLetters);
    }
    
    private static class Box {
        
        private Map<Character, Integer> numberOfLetters;
        
        public Box(String boxId) {
            this.numberOfLetters = new HashMap<Character, Integer>();
            for (int i = 0; i < boxId.length(); i++) {
                char c = boxId.charAt(i);
                if (this.numberOfLetters.containsKey(c)) {
                    this.numberOfLetters.put(c, this.numberOfLetters.get(c) + 1);
                } else {
                    this.numberOfLetters.put(c, 1);
                }
            }
        }
        
        public boolean hasOccurances(Integer numberOf) {
            for (Integer occurance : this.numberOfLetters.values()) {
                if (occurance == numberOf) {
                    return true;
                }
            }
            return false;
        }
        
    }

}
