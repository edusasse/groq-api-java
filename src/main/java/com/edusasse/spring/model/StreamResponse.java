package com.edusasse.spring.model;

import lombok.Data;

import java.util.List;

@Data
public class StreamResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<StreamChoice> choices;
}

   