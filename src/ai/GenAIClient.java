package ai;

import util.ResponseParser;
import util.InternetConnectivityChecker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * SOLID - Single Responsibility Principle
 * Responsible ONLY for communicating with GenAI API.
 * Handles offline gracefully by returning empty optional.
 */
public class GenAIClient {

  private static final String API_KEY = System.getenv("GROQ_API_KEY");
  private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

  /**
   * Generate code from prompt if internet is available.
   * Returns empty Optional if offline or API call fails.
   */
  public static Optional<String> generateCode(String finalPrompt) {
    // Check internet connectivity first
    if (!InternetConnectivityChecker.isInternetAvailable()) {
      System.out.println("[GenAIClient] No internet connection. Offline mode active.");
      return Optional.empty();
    }

    if (API_KEY == null || API_KEY.isEmpty()) {
      System.out.println("[GenAIClient] API key not set. Offline mode active.");
      return Optional.empty();
    }

    try {
      String escapedPrompt = escapeJson(finalPrompt);

      String requestBody = "{"
          + "\"model\":\"llama-3.1-8b-instant\","
          + "\"messages\":["
          + " {\"role\":\"system\",\"content\":\"You are a senior Java DSA engineer. Generate ONLY clean, complete Java code. Be thorough and complete all sections.\"},"
          + " {\"role\":\"user\",\"content\":\"" + escapedPrompt + "\"}"
          + "],"
          + "\"temperature\":0.2,"
          + "\"max_tokens\":2048"
          + "}";

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(API_URL))
          .header("Content-Type", "application/json")
          .header("Authorization", "Bearer " + API_KEY)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();

      HttpClient client = HttpClient.newHttpClient();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      String result = ResponseParser.extractGroqCode(response.body());
      return Optional.of(result);

    } catch (Exception e) {
      System.err.println("[GenAIClient] Error generating code: " + e.getMessage());
      return Optional.empty();
    }
  }

  private static String escapeJson(String text) {
    return text
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "");
  }
}
