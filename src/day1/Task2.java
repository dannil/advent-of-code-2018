package day1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Task2 {

    private static Set<Integer> encounteredFrequencies = new HashSet<>();
    
    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        
        List<String> frequencies = Files.readAllLines(Paths.get(absolutePath, "src/day1", "input.txt"));
        List<Integer> frequenciesAsInts = frequencies.stream().map(x -> Integer.valueOf(x)).collect(Collectors.toList());

        System.out.println(frequencyEncounteredTwice(frequenciesAsInts));
    }
    
    private static int frequencyEncounteredTwice(List<Integer> frequencies) {
        return frequencyEncounteredTwice(0, frequencies);
    }
    
    private static int frequencyEncounteredTwice(int summedFrequency, List<Integer> frequencies) {
        for (Integer frequency : frequencies) {
            summedFrequency += Integer.valueOf(frequency);
            if (encounteredFrequencies.contains(summedFrequency)) {
                return summedFrequency;
            }
            encounteredFrequencies.add(summedFrequency);
        }
        return frequencyEncounteredTwice(summedFrequency, frequencies);
    }

}
