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
     * Instructs AI to provide COMPLETE solution with variations and edge cases.
     * Optimized for beginner-friendly explanations without redundancy.
     */
    public static String buildPatternAugmentationPrompt(String userInput, DSAPattern pattern) {
        return """
                PROBLEM:
                %s
                
                APPLICABLE DSA PATTERN:
                Pattern Name: %s
                Category: %s
                Difficulty Level: %s
                
                BASE TEMPLATE (Already in offline mode):
                %s
                
                TASK - Generate a COMPLETE, BEGINNER-FRIENDLY Java solution:
                
                1. COMPLETE CODE:
                   - Write a full, working Java solution using this pattern
                   - Include all edge cases handling
                   - Add real problem-solving logic (not just template)
                   - Use meaningful variable names
                   - Make it copy-paste ready
                
                2. STEP-BY-STEP WALKTHROUGH:
                   - Explain the algorithm in simple terms (for beginners)
                   - Break down each key step
                   - Show how the pattern applies to THIS specific problem
                   - Use real example values to trace execution
                
                3. VARIATIONS & OPTIMIZATIONS:
                   - Show 1-2 alternative approaches
                   - Compare time/space trade-offs
                   - When to use each variation
                
                4. COMMON MISTAKES & TIPS:
                   - Pitfalls specific to beginners
                   - Debugging tips
                   - Interview follow-up questions
                
                Format the response clearly with sections: SOLUTION | WALKTHROUGH | ALTERNATIVES | TIPS
                Focus on clarity and completeness. Assume reader is a beginner learning DSA.
                """.formatted(userInput, pattern.getKey(), pattern.getCategory(), 
                             pattern.getDifficulty(), pattern.getJavaTemplate());
    }
}
