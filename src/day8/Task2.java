package day8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        Queue<Integer> input = new ArrayDeque<>();
        try (Scanner sc = new Scanner(Paths.get(absolutePath, "src/day8", "input.txt"))) {
            while (sc.hasNext()) {
                input.add(sc.nextInt());
            }
        }
        Node root = getNode(input);
        System.out.println(root.getValue());
    }

    private static Node getNode(Queue<Integer> input) {
        int numberOfChildren = input.remove();
        int numberOfMetadatas = input.remove();
        Node n = new Node();
        for (int i = 0; i < numberOfChildren; i++) {
            n.children.add(getNode(input));
        }
        for (int i = 0; i < numberOfMetadatas; i++) {
            n.metadatas.add(input.remove());
        }
        return n;
    }

    private static class Node {
        private List<Node> children;
        private List<Integer> metadatas;

        public Node() {
            this.children = new ArrayList<>();
            this.metadatas = new ArrayList<>();
        }

        public int getMetadataSum() {
            int metadataSum = metadatas.stream().mapToInt(Integer::valueOf).sum();
            for (Node child : children) {
                metadataSum += child.getMetadataSum();
            }
            return metadataSum;
        }

        public int getValue() {
            if (children.isEmpty()) {
                return getMetadataSum();
            }
            int valueSum = 0;
            for (Integer metadata : metadatas) {
                if (metadata - 1 < children.size()) {
                    valueSum += children.get(metadata - 1).getValue();
                }
            }
            return valueSum;
        }

    }

}
