package com.edusasse.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {
    private List<Message> messages = new ArrayList<>();
    private String model;
    private Double temperature;

    // Must be named "max_tokens" for the API
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    @JsonProperty("top_p")
    private Double topP;
    private Boolean stream;
    private String stop;

    public ChatRequest withMessage(String role, String content) {
        this.messages.add(new Message(role, content));
        return this;
    }

    public ChatRequest withModel(String model) {
        this.model = model;
        return this;
    }

    public ChatRequest withTemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public ChatRequest withMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
        return this;
    }

    public ChatRequest withTopP(Double topP) {
        this.topP = topP;
        return this;
    }

    public ChatRequest withStream(Boolean stream) {
        this.stream = stream;
        return this;
    }

    public ChatRequest withStop(String stop) {
        this.stop = stop;
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getModel() {
        return model;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public Double getTopP() {
        return topP;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public String getStop() {
        return stop;
    }
}

   