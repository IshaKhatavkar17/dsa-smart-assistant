package pattern;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SOLID - Single Responsibility Principle
 * Responsible ONLY for searching patterns.
 * Delegates loading to PatternSource, indexing to TrieIndex.
 */
public class PatternSearchService {
    private final PatternSource source;
    private final TrieIndex trieIndex;

    public PatternSearchService(PatternSource source) {
        this.source = source;
        this.trieIndex = new TrieIndex();
        initialize();
    }

    private void initialize() {
        source.loadPatterns();
        if (source instanceof FilePatternSource) {
            FilePatternSource fileSource = (FilePatternSource) source;
            trieIndex.indexAll(fileSource.getAllPatterns().keySet());
        }
    }

    /**
     * Find pattern by exact key match.
     */
    public Optional<DSAPattern> findByKey(String key) {
        return source.findByKey(key);
    }

    /**
     * Search patterns by prefix (using Trie).
     * Fast O(m) search where m is prefix length.
     */
    public List<DSAPattern> searchByPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> matchingKeys = trieIndex.search(prefix);
        return matchingKeys.stream()
            .map(key -> source.findByKey(key))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    /**
     * Search patterns by keyword (semantic search).
     * Searches description, when_to_use, and examples.
     */
    public List<DSAPattern> searchByKeyword(String keyword) {
        List<DSAPattern> results = source.searchByKeyword(keyword);
        // Sort by relevance (exact matches first, then partial matches)
        return results.stream()
            .sorted((a, b) -> {
                boolean aExact = a.getKey().equalsIgnoreCase(keyword);
                boolean bExact = b.getKey().equalsIgnoreCase(keyword);
                return Boolean.compare(bExact, aExact);
            })
            .collect(Collectors.toList());
    }

    /**
     * Get patterns by category.
     */
    public List<DSAPattern> getByCategory(String category) {
        return source.getByCategory(category);
    }

    /**
     * Fuzzy search combining prefix and keyword matching.
     */
    public List<DSAPattern> fuzzySearch(String query) {
        Set<DSAPattern> results = new HashSet<>();
        
        // Try prefix search first
        results.addAll(searchByPrefix(query));
        
        // If few results, add keyword search
        if (results.size() < 3) {
            results.addAll(searchByKeyword(query));
        }
        
        return new ArrayList<>(results);
    }

    /**
     * Get all available patterns.
     */
    public List<DSAPattern> getAllPatterns() {
        if (source instanceof FilePatternSource) {
            return new ArrayList<>(((FilePatternSource) source).getAllPatterns().values());
        }
        return Collections.emptyList();
    }
}
