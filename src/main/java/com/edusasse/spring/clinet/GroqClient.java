package com.edusasse.spring.clinet;

import com.edusasse.spring.model.ChatCompletion;
import com.edusasse.spring.model.ChatRequest;
import com.edusasse.spring.model.Delta;
import com.edusasse.spring.model.StreamResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

/**
 * GroqClient is a client for interacting with the Groq API to create chat completions.
 */
public class GroqClient {
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private final String apiKey;
    private final HttpClient client;
    private final ObjectMapper mapper;

    /**
     * Constructs a new GroqClient with the specified API key.
     *
     * @param apiKey the API key to use for authentication
     */
    public GroqClient(String apiKey) {
        this.apiKey = apiKey;
        // Force HTTP/1.1 to avoid issues with servers or proxies that drop HTTP/2
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Creates a chat completion using the specified request.
     *
     * @param request the chat request
     * @return the chat completion
     * @throws Exception if an error occurs during the API call
     */
    public ChatCompletion createChatCompletion(ChatRequest request) throws Exception {
        if (Boolean.TRUE.equals(request.getStream())) {
            throw new IllegalArgumentException(
                    "For streaming requests, use createStreamingChatCompletion method");
        }

        String jsonRequest = mapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1) // Force HTTP/1.1 on this request as well
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response =
                client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API call failed with status " +
                    response.statusCode() + ": " + response.body());
        }

        return mapper.readValue(response.body(), ChatCompletion.class);
    }

    /**
     * Creates a streaming chat completion using the specified request and processes the response
     * with the provided consumer.
     *
     * @param request the chat request
     * @param onMessage the consumer to process each message from the stream
     * @throws Exception if an error occurs during the API call
     */
    public void createStreamingChatCompletion(ChatRequest request, Consumer<String> onMessage) throws Exception {
        if (!Boolean.TRUE.equals(request.getStream())) {
            request.setStream(true);
        }

        String jsonRequest = mapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<InputStream> response =
                client.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API call failed with status " + response.statusCode());
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.isBlank()) {
                    continue;
                }
                // Lines from the server typically start with "data:"
                if (line.startsWith("data: ")) {
                    String data = line.substring("data: ".length());
                    if ("[DONE]".equals(data)) {
                        break;
                    }
                    try {
                        StreamResponse streamResponse = mapper.readValue(data, StreamResponse.class);
                        if (streamResponse.getChoices() != null && !streamResponse.getChoices().isEmpty()) {
                            Delta delta = streamResponse.getChoices().get(0).getDelta();
                            if (delta != null && delta.getContent() != null) {
                                onMessage.accept(delta.getContent());
                            }
                        }
                    } catch (Exception e) {
                        // If partial or invalid JSON arrives, handle or ignore
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}