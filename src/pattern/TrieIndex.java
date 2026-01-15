package pattern;

import java.util.*;

/**
 * SOLID - Single Responsibility Principle
 * Responsible ONLY for indexing patterns for fast prefix-based lookup.
 * Uses Trie data structure for efficient key searching.
 */
public class TrieIndex {
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> keys = new ArrayList<>();
    }

    private final TrieNode root = new TrieNode();

    /**
     * Index a pattern key in the Trie.
     */
    public void index(String key) {
        if (key == null || key.isEmpty()) return;

        TrieNode current = root;
        for (char c : key.toUpperCase().toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
            current.keys.add(key);
        }
    }

    /**
     * Find all pattern keys matching a prefix.
     */
    public List<String> search(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return Collections.emptyList();
        }

        TrieNode current = root;
        for (char c : prefix.toUpperCase().toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                return Collections.emptyList();
            }
        }

        // Return unique keys from this node
        return new ArrayList<>(new HashSet<>(current.keys));
    }

    /**
     * Index all patterns at once.
     */
    public void indexAll(Collection<String> keys) {
        for (String key : keys) {
            index(key);
        }
    }

    /**
     * Clear the index.
     */
    public void clear() {
        root.children.clear();
        root.keys.clear();
    }
}
