package engine;

import dsa.Trie;
import dsa.AlgorithmGraph;
import pattern.OfflinePatternEngine;
import pattern.OfflinePatternEngine.PatternResult;

import java.util.List;

/**
 * SOLID - Single Responsibility Principle
 * Delegates pattern searching to OfflinePatternEngine.
 * Maintains backward compatibility with code suggestion features.
 */
public class AutocompleteEngine {

    private final Trie trie = new Trie();
    private final AlgorithmGraph graph = new AlgorithmGraph();
    private final OfflinePatternEngine patternEngine = new OfflinePatternEngine();

    public AutocompleteEngine() {
        // DSA code patterns for basic autocomplete
        trie.insert("for(int i=0;i<n;i++)");
        trie.insert("while(left<=right)");
        trie.insert("if(map.containsKey(key))");
        trie.insert("HashMap<Integer,Integer>");
    }

    /**
     * Get code suggestions from Trie (legacy feature).
     */
    public List<String> getCodeSuggestions(String input) {
        return trie.autocomplete(input);
    }

    /**
     * Get algorithm hints from graph (legacy feature).
     */
    public List<String> getAlgorithmHints(String problem) {
        return graph.getAlgorithms(problem);
    }

    /**
     * MAIN FEATURE: Search offline patterns repository.
     */
    public List<PatternResult> searchOfflinePatterns(String query) {
        return patternEngine.searchPatterns(query);
    }

    /**
     * Find exact pattern by key.
     */
    public PatternResult findPattern(String key) {
        return patternEngine.findPattern(key)
            .orElse(null);
    }

    /**
     * Get patterns by category.
     */
    public List<PatternResult> getPatternsByCategory(String category) {
        return patternEngine.getByCategory(category);
    }

    /**
     * Get all available pattern names for UI autocomplete.
     */
    public List<String> getAllPatternNames() {
        return patternEngine.getAllPatternKeys();
    }
}
