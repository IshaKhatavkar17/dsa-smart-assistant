package model;

import pattern.DSAPattern;

/**
 * Represents a pattern result structured for tab-based UI display.
 * Separates OFFLINE content from AI-augmented content with clear labels.
 */
public class TabularPatternResult {
    
    // OFFLINE CONTENT (always available and correct)
    private String overviewTab;        // Pattern name, category, aliases
    private String explanationTab;     // WHEN_TO_USE + INTUITION
    private String javaTemplateTab;    // Code template
    private String complexityTab;      // Time and Space complexity
    
    // AI-AUGMENTED CONTENT (only if online)
    private String aiInsightsTab;      // Optional: AI variations, optimizations
    private boolean isOnline;
    
    public TabularPatternResult() {
    }
    
    public TabularPatternResult(String overviewTab, String explanationTab, 
                               String javaTemplateTab, String complexityTab) {
        this.overviewTab = overviewTab;
        this.explanationTab = explanationTab;
        this.javaTemplateTab = javaTemplateTab;
        this.complexityTab = complexityTab;
        this.isOnline = false;
    }
    
    // Getters and Setters
    public String getOverviewTab() { return overviewTab; }
    public void setOverviewTab(String overviewTab) { this.overviewTab = overviewTab; }
    
    public String getExplanationTab() { return explanationTab; }
    public void setExplanationTab(String explanationTab) { this.explanationTab = explanationTab; }
    
    public String getJavaTemplateTab() { return javaTemplateTab; }
    public void setJavaTemplateTab(String javaTemplateTab) { this.javaTemplateTab = javaTemplateTab; }
    
    public String getComplexityTab() { return complexityTab; }
    public void setComplexityTab(String complexityTab) { this.complexityTab = complexityTab; }
    
    public String getAiInsightsTab() { return aiInsightsTab; }
    public void setAiInsightsTab(String aiInsightsTab) { this.aiInsightsTab = aiInsightsTab; }
    
    public boolean isOnline() { return isOnline; }
    public void setOnline(boolean online) { isOnline = online; }
    
    /**
     * Build tab content with clear offline/online labels.
     * AVOIDS REDUNDANCY: Overview is brief, Explanation adds unique content.
     * 
     * @param pattern The DSAPattern to format
     * @param aiInsights Optional AI-augmented insights (may be null)
     * @return Formatted TabularPatternResult
     */
    public static TabularPatternResult from(DSAPattern pattern, String aiInsights, boolean isOnline) {
        TabularPatternResult result = new TabularPatternResult();
        
        // OVERVIEW TAB: Quick reference ONLY (pattern name, category, difficulty)
        StringBuilder overview = new StringBuilder();
        overview.append("PATTERN OVERVIEW\n\n");
        overview.append("Name: ").append(pattern.getName()).append("\n");
        overview.append("Category: ").append(pattern.getCategory()).append("\n");
        if (pattern.getDifficulty() != null && !pattern.getDifficulty().isEmpty()) {
            overview.append("Difficulty: ").append(pattern.getDifficulty()).append("\n");
        }
        if (pattern.getAliases() != null && !pattern.getAliases().isEmpty()) {
            overview.append("Also Known As: ").append(pattern.getAliases()).append("\n");
        }
        result.setOverviewTab(overview.toString());
        
        // EXPLANATION TAB: UNIQUE CONTENT - When to use + Intuition (NOT repeated in AI)
        StringBuilder explanation = new StringBuilder();
        if (pattern.getWhenToUse() != null && !pattern.getWhenToUse().isEmpty()) {
            explanation.append("WHEN TO USE THIS PATTERN?\n");
            explanation.append(pattern.getWhenToUse()).append("\n\n");
        }
        if (pattern.getIntuition() != null && !pattern.getIntuition().isEmpty()) {
            explanation.append("INTUITION & HOW IT WORKS:\n");
            explanation.append(pattern.getIntuition()).append("\n");
        }
        result.setExplanationTab(explanation.toString());
        
        // JAVA TEMPLATE TAB: Guaranteed correct offline template
        StringBuilder template = new StringBuilder();
        template.append("BASE TEMPLATE (Offline - Always Available)\n");
        template.append("═══════════════════════════════════════════════\n\n");
        if (pattern.getJavaTemplate() != null && !pattern.getJavaTemplate().isEmpty()) {
            template.append(pattern.getJavaTemplate()).append("\n");
        }
        result.setJavaTemplateTab(template.toString());
        
        // COMPLEXITY TAB: Performance analysis + common mistakes
        StringBuilder complexity = new StringBuilder();
        if (pattern.getTimeComplexity() != null && !pattern.getTimeComplexity().isEmpty()) {
            complexity.append("⏱️ TIME COMPLEXITY:\n").append(pattern.getTimeComplexity()).append("\n\n");
        }
        if (pattern.getSpaceComplexity() != null && !pattern.getSpaceComplexity().isEmpty()) {
            complexity.append("💾 SPACE COMPLEXITY:\n").append(pattern.getSpaceComplexity()).append("\n\n");
        }
        if (pattern.getCommonMistakes() != null && !pattern.getCommonMistakes().isEmpty()) {
            complexity.append("⚠️ COMMON MISTAKES:\n").append(pattern.getCommonMistakes()).append("\n");
        }
        result.setComplexityTab(complexity.toString());
        
        // AI INSIGHTS TAB: UNIQUE content - variations, edge cases, interview tips
        // NOT repeating what's already in offline tabs
        if (isOnline && aiInsights != null && !aiInsights.isEmpty()) {
            StringBuilder aiTab = new StringBuilder();
            aiTab.append("AI-ENHANCED INSIGHTS (Online Only)\n");
            aiTab.append("═══════════════════════════════════════════════\n\n");
            aiTab.append("This section adds:\n");
            aiTab.append("• Complete working solution with edge case handling\n");
            aiTab.append("• Step-by-step walkthrough for beginners\n");
            aiTab.append("• Alternative approaches & optimizations\n");
            aiTab.append("• Interview tips & follow-up questions\n\n");
            aiTab.append(aiInsights).append("\n");
            result.setAiInsightsTab(aiTab.toString());
        }
        
        result.setOnline(isOnline);
        return result;
    }
}
