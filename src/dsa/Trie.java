package dsa;

import java.util.*;

public class Trie {

    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            curr = curr.children.computeIfAbsent(c, k -> new TrieNode());
        }
        curr.isEnd = true;
    }

    public List<String> autocomplete(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode curr = root;

        for (char c : prefix.toCharArray()) {
            if (!curr.children.containsKey(c)) return results;
            curr = curr.children.get(c);
        }

        dfs(curr, new StringBuilder(prefix), results);
        return results;
    }

    private void dfs(TrieNode node, StringBuilder path, List<String> res) {
        if (node.isEnd) res.add(path.toString());
        for (char c : node.children.keySet()) {
            path.append(c);
            dfs(node.children.get(c), path, res);
            path.deleteCharAt(path.length() - 1);
        }
    }
} 
