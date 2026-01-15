package pattern;

import java.util.*;

/**
 * SOLID - Facade Pattern + Single Responsibility
 * Orchestrates offline pattern retrieval.
 * Provides single point of entry for pattern searching.
 */
public class OfflinePatternEngine {
    private final PatternSearchService searchService;
    private final Map<String, DSAPattern> cache = new HashMap<>();

    public OfflinePatternEngine() {
        this(new FilePatternSource());
    }

    public OfflinePatternEngine(PatternSource source) {
        this.searchService = new PatternSearchService(source);
        prewarmCache();
    }

    /**
     * Load all patterns into memory for fastest retrieval.
     */
    private void prewarmCache() {
        List<DSAPattern> all = searchService.getAllPatterns();
        all.forEach(p -> cache.put(p.getKey(), p));
        System.out.println("[OfflinePatternEngine] Cache prewarmed with " + cache.size() + " patterns");
    }

    /**
     * Find pattern by exact key.
     * Returns wrapped result for UI consistency.
     */
    public Optional<PatternResult> findPattern(String key) {
        Optional<DSAPattern> pattern = searchService.findByKey(key);
        return pattern.map(p -> new PatternResult(p, PatternResult.Source.OFFLINE));
    }

    /**
     * Search patterns intelligently.
     * - First tries exact prefix match
     * - Then tries keyword search
     * - Returns top 5 results
     */
    public List<PatternResult> searchPatterns(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Try prefix search (fast Trie lookup)
        List<DSAPattern> prefixResults = searchService.searchByPrefix(query);
        
        // If prefix has matches, return top 5
        if (!prefixResults.isEmpty()) {
            return prefixResults.stream()
                .limit(5)
                .map(p -> new PatternResult(p, PatternResult.Source.OFFLINE))
                .toList();
        }

        // Fall back to keyword search
        List<DSAPattern> keywordResults = searchService.searchByKeyword(query);
        return keywordResults.stream()
            .limit(5)
            .map(p -> new PatternResult(p, PatternResult.Source.OFFLINE))
            .toList();
    }

    /**
     * Get patterns by category.
     */
    public List<PatternResult> getByCategory(String category) {
        return searchService.getByCategory(category).stream()
            .map(p -> new PatternResult(p, PatternResult.Source.OFFLINE))
            .toList();
    }

    /**
     * Get all available pattern keys (for UI autocomplete).
     */
    public List<String> getAllPatternKeys() {
        return new ArrayList<>(cache.keySet());
    }

    /**
     * Check if pattern exists.
     */
    public boolean patternExists(String key) {
        return cache.containsKey(key);
    }

    /**
     * Wrapper for pattern results with source information.
     */
    public static class PatternResult {
        public enum Source {
            OFFLINE, AI_AUGMENTED
        }

        public final DSAPattern pattern;
        public final Source source;
        public String aiResponse; // Optional AI augmentation

        public PatternResult(DSAPattern pattern, Source source) {
            this.pattern = pattern;
            this.source = source;
        }

        public void appendAiResponse(String response) {
            this.aiResponse = response;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n╔═══════════════════════════════════════════════╗\n");
            sb.append(String.format("║ [%s] %s ║\n", source, pattern.getKey()));
            sb.append("╚═══════════════════════════════════════════════╝\n");
            sb.append(pattern.toString());

            if (aiResponse != null && !aiResponse.isEmpty()) {
                sb.append("\n╔═══════════════════════════════════════════════╗\n");
                sb.append("║ AI-AUGMENTED SOLUTION\n");
                sb.append("╚═══════════════════════════════════════════════╝\n");
                sb.append(aiResponse);
            }

            return sb.toString();
        }
    }
}
