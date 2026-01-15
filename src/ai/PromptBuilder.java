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
                PROBLEM STATEMENT:
                %s
                
                SUGGESTED DSA APPROACHES:
                %s
                
                GENERATE A COMPLETE SOLUTION:
                1. Write full, working Java code that solves the problem
                2. Choose the best approach from suggestions
                3. Include meaningful variable names and clear logic
                4. Add brief inline comments for key steps
                5. Handle edge cases
                6. Make it production-ready and copy-paste ready
                
                Format: Provide only the complete Java solution code. No explanations, just the code.
                """.formatted(userInput, hints);
    }

    /**
     * Build prompt from offline pattern.
     * Instructs AI to provide COMPLETE solution with clear formatting.
     * Optimized for beginner-friendly explanations and complete responses.
     */
    public static String buildPatternAugmentationPrompt(String userInput, DSAPattern pattern) {
        return """
                PROBLEM: %s
                PATTERN: %s (%s)
                BASE TEMPLATE:
                %s
                
                GENERATE EXACTLY THIS FORMAT (complete and thorough):
                
                === COMPLETE SOLUTION ===
                Provide a full, working Java solution using this pattern with comments. Handle all edge cases.
                
                === WALKTHROUGH (BEGINNER-FRIENDLY) ===
                Step-by-step explanation of how the algorithm works. Use concrete examples.
                
                === KEY INSIGHTS ===
                Why this pattern is effective. When and where to use it. Common pitfalls.
                
                === FOLLOW-UP VARIANTS ===
                Show 1-2 similar problems or variations you can solve with this pattern.
                
                Make your response COMPLETE and detailed in each section. No truncation.
                """.formatted(userInput, pattern.getKey(), pattern.getCategory(), 
                             pattern.getJavaTemplate());
    }
}
