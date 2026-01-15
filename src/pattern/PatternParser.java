package pattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses DSA pattern files with the enhanced format.
 * Extracts fields: KEY, ALIASES, CATEGORY, WHEN_TO_USE, INTUITION, 
 * JAVA_TEMPLATE, TIME_COMPLEXITY, SPACE_COMPLEXITY, COMMON_MISTAKES
 */
public class PatternParser {
    
    private static final String FIELD_SEPARATOR = "^";
    
    /**
     * Parse a pattern file content into DSAPattern object.
     * 
     * @param content Raw file content
     * @return DSAPattern with all fields populated
     */
    public static DSAPattern parse(String content) {
        Map<String, String> fields = extractFields(content);
        
        DSAPattern pattern = new DSAPattern();
        pattern.setKey(getField(fields, "KEY"));
        pattern.setName(pattern.getKey());
        pattern.setCategory(getField(fields, "CATEGORY"));
        pattern.setDescription(getField(fields, "INTUITION")); // Beginner-friendly explanation
        
        // Store additional fields for UI display
        pattern.setAliases(getField(fields, "ALIASES"));
        pattern.setWhenToUse(getField(fields, "WHEN_TO_USE"));
        pattern.setIntuition(getField(fields, "INTUITION"));
        pattern.setJavaTemplate(getField(fields, "JAVA_TEMPLATE"));
        pattern.setTimeComplexity(getField(fields, "TIME_COMPLEXITY"));
        pattern.setSpaceComplexity(getField(fields, "SPACE_COMPLEXITY"));
        pattern.setCommonMistakes(getField(fields, "COMMON_MISTAKES"));
        
        return pattern;
    }
    
    /**
     * Extract all fields from pattern content.
     * Field format: KEY: value ... VALUE ... KEY: next_value
     * 
     * @param content File content
     * @return Map of field names to values
     */
    private static Map<String, String> extractFields(String content) {
        Map<String, String> fields = new HashMap<>();
        
        // Define field order for parsing
        String[] fieldNames = {
            "KEY", "ALIASES", "CATEGORY", "DIFFICULTY", 
            "WHEN_TO_USE", "INTUITION", "JAVA_TEMPLATE",
            "TIME_COMPLEXITY", "SPACE_COMPLEXITY", "COMMON_MISTAKES"
        };
        
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            String nextFieldName = (i < fieldNames.length - 1) ? fieldNames[i + 1] : null;
            
            String value = extractFieldValue(content, fieldName, nextFieldName);
            if (value != null && !value.trim().isEmpty()) {
                fields.put(fieldName, value.trim());
            }
        }
        
        return fields;
    }
    
    /**
     * Extract value for a specific field.
     * 
     * @param content Full content
     * @param fieldName Field to extract
     * @param nextFieldName Field that marks end of current field
     * @return Field value or empty string
     */
    private static String extractFieldValue(String content, String fieldName, String nextFieldName) {
        String pattern = fieldName + ":";
        int startIdx = content.indexOf(pattern);
        
        if (startIdx == -1) {
            return "";
        }
        
        int valueStart = startIdx + pattern.length();
        int endIdx = content.length();
        
        if (nextFieldName != null) {
            int nextIdx = content.indexOf(nextFieldName + ":", valueStart);
            if (nextIdx != -1) {
                endIdx = nextIdx;
            }
        }
        
        return content.substring(valueStart, endIdx).trim();
    }
    
    /**
     * Get field value with fallback to empty string.
     * 
     * @param fields Field map
     * @param key Field key
     * @return Field value or empty string
     */
    private static String getField(Map<String, String> fields, String key) {
        return fields.getOrDefault(key, "");
    }
}
