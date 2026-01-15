package pattern;

/**
 * Domain model representing a single DSA pattern.
 * Contains all metadata and template information.
 * Enhanced format supports ALIASES, INTUITION, and COMMON_MISTAKES.
 */
public class DSAPattern {
    private String key;
    private String name;
    private String aliases;
    private String category;
    private String difficulty;
    private String description;
    private String intuition;
    private String whenToUse;
    private String javaTemplate;
    private String timeComplexity;
    private String spaceComplexity;
    private String commonMistakes;
    private String exampleProblems;

    // Default constructor for Parser usage
    public DSAPattern() {
    }

    public DSAPattern(String key, String category, String difficulty,
                      String description, String whenToUse,
                      String javaTemplate, String timeComplexity,
                      String spaceComplexity, String exampleProblems) {
        this.key = key;
        this.name = key;
        this.category = category;
        this.difficulty = difficulty;
        this.description = description;
        this.whenToUse = whenToUse;
        this.javaTemplate = javaTemplate;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.exampleProblems = exampleProblems;
    }

    // Getters and Setters
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getName() { return name != null ? name : key; }
    public void setName(String name) { this.name = name; }

    public String getAliases() { return aliases; }
    public void setAliases(String aliases) { this.aliases = aliases; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIntuition() { return intuition; }
    public void setIntuition(String intuition) { this.intuition = intuition; }

    public String getWhenToUse() { return whenToUse; }
    public void setWhenToUse(String whenToUse) { this.whenToUse = whenToUse; }

    public String getJavaTemplate() { return javaTemplate; }
    public void setJavaTemplate(String javaTemplate) { this.javaTemplate = javaTemplate; }

    public String getTimeComplexity() { return timeComplexity; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }

    public String getSpaceComplexity() { return spaceComplexity; }
    public void setSpaceComplexity(String spaceComplexity) { this.spaceComplexity = spaceComplexity; }

    public String getCommonMistakes() { return commonMistakes; }
    public void setCommonMistakes(String commonMistakes) { this.commonMistakes = commonMistakes; }

    public String getExampleProblems() { return exampleProblems; }
    public void setExampleProblems(String exampleProblems) { this.exampleProblems = exampleProblems; }

    @Override
    public String toString() {
        return """
                ========== %s ==========
                Category: %s | Difficulty: %s
                Time: %s | Space: %s
                
                Description:
                %s
                
                When to use:
                %s
                
                Java Template:
                %s
                
                Example Problems:
                %s
                """.formatted(key, category, difficulty, timeComplexity, spaceComplexity,
                        description, whenToUse, javaTemplate, exampleProblems);
    }
}
