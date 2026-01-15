package pattern;

import java.util.Optional;

/**
 * SOLID - Interface Segregation Principle
 * Defines contract for pattern sources (offline/online).
 * Clients depend on abstractions, not concrete implementations.
 */
public interface PatternSource {

    /**
     * Find a pattern by its key (e.g., TWO_POINTERS).
     *
     * @param key Pattern identifier
     * @return Pattern details if found
     */
    Optional<DSAPattern> findByKey(String key);

    /**
     * Search patterns by keyword.
     *
     * @param keyword Search term
     * @return List of matching patterns
     */
    java.util.List<DSAPattern> searchByKeyword(String keyword);

    /**
     * Get all patterns in a category.
     *
     * @param category Category name (e.g., "array", "dp")
     * @return List of patterns in category
     */
    java.util.List<DSAPattern> getByCategory(String category);

    /**
     * Load all patterns.
     */
    void loadPatterns();

    /**
     * Check if patterns are loaded.
     */
    boolean isLoaded();
}
