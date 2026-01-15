package util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Utility to check internet connectivity for online/offline mode switching.
 * Uses multiple strategies for reliability.
 */
public class InternetConnectivityChecker {
    private static final int TIMEOUT_MS = 3000;
    private static final String[] DNS_SERVERS = {
        "8.8.8.8:53",      // Google DNS
        "1.1.1.1:53"       // Cloudflare DNS
    };
    private static final String CONNECTIVITY_CHECK_URL = "https://www.google.com";

    /**
     * Check if internet is available (blocking operation).
     * Should be called on a background thread.
     */
    public static boolean isInternetAvailable() {
        // Strategy 1: DNS lookup
        if (canReachDNS()) {
            return true;
        }

        // Strategy 2: HTTP HEAD request
        if (canMakeHttpRequest()) {
            return true;
        }

        return false;
    }

    /**
     * Check DNS connectivity (faster, no HTTP overhead).
     */
    private static boolean canReachDNS() {
        for (String dnsServer : DNS_SERVERS) {
            String[] parts = dnsServer.split(":");
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(parts[0], Integer.parseInt(parts[1])), TIMEOUT_MS);
                return true;
            } catch (Exception ignored) {
                // Try next DNS
            }
        }
        return false;
    }

    /**
     * Check HTTP connectivity (reliable but slower).
     */
    private static boolean canMakeHttpRequest() {
        try {
            HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(TIMEOUT_MS))
                .build();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CONNECTIVITY_CHECK_URL))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .timeout(Duration.ofMillis(TIMEOUT_MS))
                .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() >= 200 && response.statusCode() < 400;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Async version - checks connectivity without blocking.
     */
    public static void checkInternetAsync(InternetAvailabilityListener listener) {
        new Thread(() -> {
            boolean available = isInternetAvailable();
            listener.onResult(available);
        }).start();
    }

    @FunctionalInterface
    public interface InternetAvailabilityListener {
        void onResult(boolean isAvailable);
    }
}
