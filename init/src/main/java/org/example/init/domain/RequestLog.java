package org.example.init.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String company;
    private String model;
    private String prompt;
    @Column(length = 1000)
    private String response;
    private int tokens;
    private LocalDateTime timestamp;
}