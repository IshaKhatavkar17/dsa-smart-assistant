package pattern;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SOLID - Single Responsibility Principle
 * Responsible ONLY for loading patterns from files.
 * Delegates caching to TrieIndex, searching to PatternSearchService.
 */
public class FilePatternSource implements PatternSource {
    private static final String RESOURCES_BASE = "resources";
    private final Map<String, DSAPattern> patterns = new ConcurrentHashMap<>();
    private boolean isLoaded = false;

    @Override
    public void loadPatterns() {
        if (isLoaded) return;

        try {
            Path resourcesPath = Paths.get(RESOURCES_BASE);
            if (!Files.exists(resourcesPath)) {
                System.err.println("[FilePatternSource] Resource directory not found: " + resourcesPath.toAbsolutePath());
                isLoaded = true;
                return;
            }

            // Load all .txt files from all subdirectories
            try (Stream<Path> paths = Files.walk(resourcesPath)) {
                paths.filter(p -> p.toString().endsWith(".txt"))
                     .forEach(this::loadPatternFile);
            }
            isLoaded = true;
            System.out.println("[FilePatternSource] Loaded " + patterns.size() + " patterns");
        } catch (IOException e) {
            System.err.println("[FilePatternSource] Error loading patterns: " + e.getMessage());
            isLoaded = true;
        }
    }

    private void loadPatternFile(Path filePath) {
        try {
            String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
            DSAPattern pattern = PatternParser.parse(content);
            if (pattern != null && pattern.getKey() != null && !pattern.getKey().isEmpty()) {
                patterns.put(pattern.getKey().toUpperCase(), pattern);
            }
        } catch (IOException e) {
            System.err.println("[FilePatternSource] Error reading " + filePath + ": " + e.getMessage());
        }
    }

    private DSAPattern parsePatternFile(String content) {
        // Delegate to PatternParser for enhanced format
        return PatternParser.parse(content);
    }

    @Override
    public Optional<DSAPattern> findByKey(String key) {
        if (!isLoaded) loadPatterns();
        return Optional.ofNullable(patterns.get(key.toUpperCase()));
    }

    @Override
    public List<DSAPattern> searchByKeyword(String keyword) {
        if (!isLoaded) loadPatterns();
        String lowerKeyword = keyword.toLowerCase();
        
        return patterns.values().stream()
            .filter(p -> p.getKey().toLowerCase().contains(lowerKeyword) ||
                        (p.getAliases() != null && p.getAliases().toLowerCase().contains(lowerKeyword)) ||
                        (p.getDescription() != null && p.getDescription().toLowerCase().contains(lowerKeyword)) ||
                        (p.getWhenToUse() != null && p.getWhenToUse().toLowerCase().contains(lowerKeyword)) ||
                        (p.getExampleProblems() != null && p.getExampleProblems().toLowerCase().contains(lowerKeyword)))
            .collect(Collectors.toList());
    }

    @Override
    public List<DSAPattern> getByCategory(String category) {
        if (!isLoaded) loadPatterns();
        String normalizedCategory = category.toLowerCase();
        
        return patterns.values().stream()
            .filter(p -> p.getCategory().toLowerCase().contains(normalizedCategory))
            .collect(Collectors.toList());
    }

    @Override
    public boolean isLoaded() {
        return isLoaded;
    }

    public Map<String, DSAPattern> getAllPatterns() {
        if (!isLoaded) loadPatterns();
        return new HashMap<>(patterns);
    }
}
