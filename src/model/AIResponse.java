package model;

import pattern.DSAPattern;

/**
 * SOLID - Single Responsibility Principle
 * Represents a response that can be either offline pattern or AI-augmented.
 */
public class AIResponse {
    public enum ResponseMode {
        OFFLINE_ONLY,          // Only offline pattern found
        AI_AUGMENTED,          // Offline + AI enhancement
        AI_ONLY,               // Only AI response (no pattern found)
        OFFLINE_MODE_DISABLED  // Offline unavailable
    }

    private ResponseMode mode;
    private DSAPattern offlinePattern;
    private String aiResponse;
    private String userQuery;

    public AIResponse(String userQuery) {
        this.userQuery = userQuery;
        this.mode = ResponseMode.OFFLINE_MODE_DISABLED;
    }

    // Setters for building response
    public void setOfflinePattern(DSAPattern pattern) {
        this.offlinePattern = pattern;
        updateMode();
    }

    public void setAiResponse(String response) {
        this.aiResponse = response;
        updateMode();
    }

    private void updateMode() {
        if (offlinePattern != null && aiResponse != null) {
            mode = ResponseMode.AI_AUGMENTED;
        } else if (offlinePattern != null) {
            mode = ResponseMode.OFFLINE_ONLY;
        } else if (aiResponse != null) {
            mode = ResponseMode.AI_ONLY;
        }
    }

    // Getters
    public ResponseMode getMode() { return mode; }
    public DSAPattern getOfflinePattern() { return offlinePattern; }
    public String getAiResponse() { return aiResponse; }
    public String getUserQuery() { return userQuery; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        switch (mode) {
            case OFFLINE_ONLY:
                sb.append("╔════════════════════════════════════════════════╗\n");
                sb.append("║          🔒 OFFLINE MODE - PATTERN ONLY        ║\n");
                sb.append("╚════════════════════════════════════════════════╝\n");
                sb.append(offlinePattern.toString());
                break;

            case AI_AUGMENTED:
                sb.append("╔════════════════════════════════════════════════╗\n");
                sb.append("║     🚀 AI-AUGMENTED MODE - HYBRID RESPONSE     ║\n");
                sb.append("╚════════════════════════════════════════════════╝\n");
                sb.append("\n[OFFLINE FOUNDATION]\n");
                sb.append(offlinePattern.toString());
                sb.append("\n\n[AI-ENHANCED SOLUTION]\n");
                sb.append(aiResponse);
                break;

            case AI_ONLY:
                sb.append("╔════════════════════════════════════════════════╗\n");
                sb.append("║        🌐 ONLINE MODE - AI ONLY                ║\n");
                sb.append("╚════════════════════════════════════════════════╝\n");
                sb.append(aiResponse);
                break;

            case OFFLINE_MODE_DISABLED:
                sb.append("No response available. Please check internet or provide valid query.");
                break;
        }

        return sb.toString();
    }
}
