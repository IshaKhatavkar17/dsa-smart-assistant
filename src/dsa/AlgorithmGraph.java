package dsa;

import java.util.*;

public class AlgorithmGraph {

    private final Map<String, List<String>> graph = new HashMap<>();

    public AlgorithmGraph() {
        graph.put("two sum", List.of("HashMap"));
        graph.put("sliding window", List.of("Two Pointers"));
        graph.put("shortest path", List.of("BFS", "Dijkstra"));
        graph.put("cycle detection", List.of("DFS", "Union Find"));
    }

    public List<String> getAlgorithms(String problem) {
        return graph.getOrDefault(problem.toLowerCase(), List.of("Try brute force first"));
    }
}
