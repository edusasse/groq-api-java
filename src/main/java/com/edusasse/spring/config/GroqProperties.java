package com.edusasse.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for Groq API integration.
 * <p>
 * This class is used to bind the properties defined in the application configuration
 * with the prefix "groq" to the fields of this class.
 * </p>
 */
@Data
@Component
@ConfigurationProperties(prefix = "groq")
public class GroqProperties {
    /**
     * The API key for authenticating with the Groq API.
     */
    private String apiKey;

    /**
     * The model to be used for chat completions.
     */
    private String model;

    /**
     * The temperature setting for the model, controlling the randomness of the output.
     */
    private double temperature;

    /**
     * The maximum number of tokens to generate in the chat completion.
     */
    private int maxTokens;

    /**
     * The top-p setting for nucleus sampling, controlling the diversity of the output.
     */
    private double topP;

    /**
     * Flag indicating whether to use streaming for chat completions.
     */
    private boolean stream;
}