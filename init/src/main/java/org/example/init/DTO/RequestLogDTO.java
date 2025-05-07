package org.example.init.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestLogDTO {
    private String model;
    private String prompt;
    private String response;
    private int tokens;
    private LocalDateTime timestamp;
}