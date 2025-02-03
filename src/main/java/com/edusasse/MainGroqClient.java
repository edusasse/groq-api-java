package com.edusasse;

import com.edusasse.spring.model.ChatRequest;
import com.edusasse.spring.clinet.GroqClient;

/**
 * Main class for demonstrating the usage of GroqClient to create chat completions.
 */
public class MainGroqClient {
    public static void main(String[] args) {
        // Replace with your real API key
        String apiKey = "<<API KEY>>";
        GroqClient client = new GroqClient(apiKey);

        // Build the request
        ChatRequest request = new ChatRequest()
                .withModel("deepseek-r1-distill-llama-70b")
                .withMessage("user", """
                        What is full of holes but still holds water?
                        """)
                .withTemperature(0.6)
                .withMaxTokens(8192) // renamed method & param -> max_tokens
                .withTopP(0.95)
                .withStream(false);   // or set .withStream(true)

        try {
            // If you really want streaming, use createStreamingChatCompletion
            // and set withStream(true)
            client.createStreamingChatCompletion(request, chunk -> {
                System.out.print(chunk);
                System.out.flush();
            });

            // Keep main thread alive briefly to allow streaming to complete
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}