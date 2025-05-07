package org.example.init.DTO;

import lombok.Data;

@Data
public class ChatRequest {
    private String prompt;
    private String model; // Ejemplo: "gpt-4"
}