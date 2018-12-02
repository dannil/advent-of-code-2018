package day1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Task1 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        int resultingFrequency = 0;
        List<String> frequencies = Files.readAllLines(Paths.get(absolutePath, "src/day1", "input.txt"));
        for (String frequency : frequencies) {
            resultingFrequency += Integer.valueOf(frequency);
        }
        System.out.println(resultingFrequency);
    }

}
