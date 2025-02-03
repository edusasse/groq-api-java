package com.edusasse.spring.service;

import com.edusasse.spring.clinet.GroqClient;
import com.edusasse.spring.model.ChatCompletion;
import com.edusasse.spring.model.ChatRequest;
import com.edusasse.spring.model.Choice;
import com.edusasse.spring.config.GroqProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for processing messages using the GroqClient.
 */
@Service
public class GroqService {

    private final GroqClient groqClient;
    private final GroqProperties groqProperties;

    /**
     * Constructs a new GroqService with the specified GroqProperties.
     *
     * @param groqProperties the properties for configuring the GroqClient
     */
    @Autowired
    public GroqService(GroqProperties groqProperties) {
        this.groqProperties = groqProperties;
        this.groqClient = new GroqClient(groqProperties.getApiKey());
    }

    /**
     * Processes the given message using GroqClient and returns the improved PostgreSQL code.
     *
     * @param message The input message to process.
     * @return The improved PostgreSQL code as a String.
     * @throws Exception If an error occurs during processing.
     */
    public String processMessage(String message) throws Exception {
        ChatRequest request = new ChatRequest()
                .withModel(groqProperties.getModel())
                .withMessage("user", message)
                .withTemperature(groqProperties.getTemperature())
                .withMaxTokens(groqProperties.getMaxTokens())
                .withTopP(groqProperties.getTopP())
                .withStream(groqProperties.isStream());

        if (groqProperties.isStream()) {
            StringBuilder responseBuilder = new StringBuilder();
            groqClient.createStreamingChatCompletion(request, chunk -> {
                responseBuilder.append(chunk);
            });
            return responseBuilder.toString().trim();
        } else {
            ChatCompletion chatCompletion = groqClient.createChatCompletion(request);
            StringBuilder result = new StringBuilder();
            for (Choice choice : chatCompletion.getChoices()) {
                if (choice.getMessage() != null && choice.getMessage().getContent() != null) {
                    result.append(choice.getMessage().getContent()).append("\n");
                }
            }
            return result.toString().trim();
        }
    }
}