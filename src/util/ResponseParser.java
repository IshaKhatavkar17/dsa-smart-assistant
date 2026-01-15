package util;

public class ResponseParser {

    /**
     * Extract content from Groq API JSON response.
     * Handles escaped characters and ensures complete response extraction.
     */
    public static String extractGroqCode(String response) {
        if (response == null || response.isEmpty()) {
            return "No response received. Please try again.";
        }

        try {
            // Find the "content" field in the JSON response
            int contentIndex = response.indexOf("\"content\":");
            if (contentIndex == -1) {
                return response; // Return as-is if not JSON formatted
            }

            // Find the start of the content string (after the opening quote)
            int start = response.indexOf("\"", contentIndex + 10) + 1;
            if (start <= contentIndex) {
                return response; // Invalid format
            }

            // Find the end of the content string
            // Need to account for escaped quotes
            int end = start;
            boolean escaped = false;
            while (end < response.length()) {
                char c = response.charAt(end);
                if (c == '\\' && !escaped) {
                    escaped = true;
                    end++;
                    continue;
                }
                if (c == '"' && !escaped) {
                    break; // Found the closing quote
                }
                escaped = false;
                end++;
            }

            if (end > start) {
                String extracted = response.substring(start, end);
                // Unescape common patterns
                extracted = extracted.replace("\\n", "\n")
                                    .replace("\\\"", "\"")
                                    .replace("\\t", "\t")
                                    .replace("\\\\", "\\");
                return extracted;
            }

            return response;
        } catch (Exception e) {
            return response; // Return original if parsing fails
        }
    }
}
