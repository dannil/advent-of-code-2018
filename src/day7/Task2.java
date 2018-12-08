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

public class Task2 {

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

        int numberOfWorkers = 5;
        Collection<Worker> workers = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            workers.add(new Worker());
        }

        List<Node> roots = nodes.values().stream().filter(x -> x.parents.size() == 0).collect(Collectors.toList());
        Set<Node> readyNodes = new HashSet<>();
        for (Node root : roots) {
            readyNodes.add(root);
        }
        int completionTime = getCompletionTime(readyNodes, nodes.values(), workers);
        System.out.println(completionTime);
    }

    private static int getCompletionTime(Set<Node> readyNodes, Collection<Node> allNodes, Collection<Worker> workers) {
        return getCompletionTime(new HashSet<>(), new HashSet<>(), readyNodes, allNodes, workers);
    }

    private static int getCompletionTime(Set<Node> completedNodes, Set<Node> workingNodes, Set<Node> readyNodes,
            Collection<Node> allNodes, Collection<Worker> workers) {
        int time = 0;
        while (completedNodes.size() != allNodes.size() || isAnyWorking(workers)) {
            time++;
            for (Worker worker : workers) {
                worker.work();
                if (worker.isAvailable() && worker.workingOn != null) {
                    completedNodes.add(worker.workingOn);
                    workingNodes.remove(worker.workingOn);
                    worker.completeWork();
                    for (Node allNode : allNodes) {
                        if (hasParentsBeenVisited(allNode, completedNodes) && !workingNodes.contains(allNode)
                                && !completedNodes.contains(allNode)) {
                            readyNodes.add(allNode);
                        }
                    }
                }
            }
            for (Worker worker : workers) {
                if (worker.isAvailable() && !readyNodes.isEmpty()) {
                    List<Node> readyNodesCopy = new ArrayList<>(readyNodes);
                    Collections.sort(readyNodesCopy);
                    Node readyNode = readyNodesCopy.get(0);
                    worker.startWorkingOn(readyNode);
                    workingNodes.add(readyNode);
                    readyNodes.remove(readyNode);
                }
            }
        }
        return time - 1;
    }

    private static boolean hasParentsBeenVisited(Node node, Set<Node> visitedNodes) {
        for (Node parent : node.parents) {
            if (!visitedNodes.contains(parent)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAnyWorking(Collection<Worker> workers) {
        for (Worker worker : workers) {
            if (!worker.isAvailable()) {
                return true;
            }
        }
        return false;
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

    private static class Worker {
        private int timeLeft;
        private Node workingOn;

        public boolean isAvailable() {
            return timeLeft == 0;
        }

        public void work() {
            if (timeLeft > 0) {
                timeLeft--;
            }
        }

        public void startWorkingOn(Node n) {
            workingOn = n;
            timeLeft = 60 - 'A' + 1 + n.value;
        }

        public void completeWork() {
            timeLeft = 0;
            workingOn = null;
        }

    }

}
