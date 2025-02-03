package com.edusasse.spring.model;

import lombok.Data;

@Data
public class StreamChoice {
    private Delta delta;
    private String finishReason;
    private Integer index;
}

 