package day2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Task2 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day2", "input.txt"));
        System.out.println(findAlmostEqualBoxes(input));
    }

    private static String findAlmostEqualBoxes(List<String> input) {
        for (String boxId1 : input) {
            Box b1 = new Box(boxId1);
            for (String boxId2 : input) {
                Box b2 = new Box(boxId2);
                String difference = b1.differsByOneLetter(b2);
                if (difference != null) {
                    return difference;
                }
            }
        }
        return null;
    }

    private static class Box {
        
        private String boxId;

        public Box(String boxId) {
            this.boxId = boxId;
        }

        private String differsByOneLetter(Box b1) {
            String commonString = "";
            
            Character[] ours = new Character[this.boxId.length()];
            Character[] theirs = new Character[b1.getBoxId().length()];
            
            for (int i = 0; i < this.boxId.length(); i++) {
                char c = this.boxId.charAt(i);
                ours[i] = c;
            }
            
            int differs = 0;
            for (int i = 0; i < b1.getBoxId().length(); i++) {
                char c = b1.getBoxId().charAt(i);
                if (!ours[i].equals(c)) {
                    differs++;
                } else {
                    commonString += c;
                }
                theirs[i] = c;
            }
            if (differs == 1) {
                return commonString;
            }
            return null;
        }

        public String getBoxId() {
            return this.boxId;
        }

    }

}
