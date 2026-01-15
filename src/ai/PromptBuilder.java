package ai;

import java.util.List;
import pattern.DSAPattern;

/**
 * SOLID - Single Responsibility Principle
 * Responsible ONLY for constructing AI prompts from patterns and user input.
 */
public class PromptBuilder {

    public static String buildPrompt(String userInput, List<String> hints) {
        return """
                Problem:
                %s

                Suggested DSA approaches:
                %s

                Generate optimized Java code using these hints.
                """.formatted(userInput, hints);
    }

    /**
     * Build prompt from offline pattern.
     * Instructs AI to refine/extend the offline template.
     */
    public static String buildPatternAugmentationPrompt(String userInput, DSAPattern pattern) {
        return """
                User Problem: %s
                
                I have already found an offline DSA pattern template for this:
                
                Pattern: %s
                Category: %s
                Difficulty: %s
                When to use: %s
                Template:
                %s
                
                Now generate an extended, optimized Java solution that:
                1. Uses the above pattern as foundation
                2. Adds more specific implementation details for the problem
                3. Includes clear comments explaining the approach
                4. Is production-ready and interview-ready
                """.formatted(userInput, pattern.getKey(), pattern.getCategory(), 
                             pattern.getDifficulty(), pattern.getWhenToUse(), 
                             pattern.getJavaTemplate());
    }
}
