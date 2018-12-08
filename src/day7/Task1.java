package day7;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Task1 {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        List<String> input = Files.readAllLines(Paths.get(absolutePath, "src/day7", "input.txt"));

        Map<Character, Node> nodes = new HashMap<>();
        for (String requirement : input) {
            char prerequisiteStep = requirement.substring(requirement.indexOf(" ") + 1).charAt(0);
            char thisNode = requirement.substring(requirement.lastIndexOf("step") + "step".length() + 1).charAt(0);

            Node parent = new Node(prerequisiteStep);
            if (nodes.containsKey(prerequisiteStep)) {
                parent = nodes.get(prerequisiteStep);
            }
            Node node = new Node(thisNode);
            if (nodes.containsKey(thisNode)) {
                node = nodes.get(thisNode);
            }
            node.addParent(parent);
            parent.addSuccessor(node);

            nodes.put(prerequisiteStep, parent);
            nodes.put(thisNode, node);
        }

        List<Node> roots = nodes.values().stream().filter(x -> x.parents.size() == 0).collect(Collectors.toList());
        Set<Node> visitedNodes = new HashSet<>();
        String instructionOrder = "";
        for (Node root : roots) {
            instructionOrder += getInstructionOrder(root, visitedNodes, nodes.values());
        }
        System.out.println(instructionOrder);
    }

    private static String getInstructionOrder(Node currentNode, Set<Node> visitedNodes, Collection<Node> allNodes) {
        return getInstructionOrder(currentNode, visitedNodes, new HashSet<>(), allNodes);
    }

    private static String getInstructionOrder(Node currentNode, Set<Node> visitedNodes, Set<Node> readyNodes,
            Collection<Node> allNodes) {
        String result = "";
        if (!visitedNodes.contains(currentNode)) {
            result += currentNode.value;
            visitedNodes.add(currentNode);
            readyNodes.remove(currentNode);
            for (Node allNode : allNodes) {
                if (hasParentsBeenVisited(allNode, visitedNodes) && !visitedNodes.contains(allNode)) {
                    readyNodes.add(allNode);
                }
            }
            List<Node> readyNodesCopy = new ArrayList<>(readyNodes);
            Collections.sort(readyNodesCopy);
            if (!readyNodesCopy.isEmpty()) {
                result += getInstructionOrder(readyNodesCopy.get(0), visitedNodes, readyNodes, allNodes);
            }
        }
        return result;
    }

    private static boolean hasParentsBeenVisited(Node node, Set<Node> visitedNodes) {
        for (Node parent : node.parents) {
            if (!visitedNodes.contains(parent)) {
                return false;
            }
        }
        return true;
    }

    private static class Node implements Comparable<Node> {
        private char value;
        private Set<Node> parents;
        private Set<Node> successors;

        public Node(char value) {
            this.value = value;
            this.parents = new HashSet<>();
            this.successors = new HashSet<>();
        }

        public void addParent(Node n) {
            if (!parents.contains(n)) {
                parents.add(n);
                n.addSuccessor(this);
            }
        }

        public void addSuccessor(Node n) {
            if (!successors.contains(n)) {
                successors.add(n);
                n.addParent(this);
            }
        }

        @Override
        public boolean equals(Object o) {
            Node n = (Node) o;
            return Objects.equals(value, n.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public int compareTo(Node o) {
            return new Character(value).compareTo(new Character(o.value));
        }

    }

}
