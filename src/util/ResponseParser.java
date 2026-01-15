package util;

public class ResponseParser {

    public static String extractGroqCode(String response) {
        if (response == null || response.isEmpty()) {
            return "No response received.";
        }

        int contentIndex = response.indexOf("\"content\":");
        if (contentIndex == -1)
            return response;

        int start = response.indexOf("\"", contentIndex + 10) + 1;
        int end = response.indexOf("\"", start);

        if (start > 0 && end > start) {
            return response.substring(start, end)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");
        }

        return response;
    }
}
