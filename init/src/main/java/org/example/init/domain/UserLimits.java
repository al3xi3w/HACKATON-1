package org.example.init.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;

@Entity
@Data
public class UserLimits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserAccount user;

    private String model; // GPT-4, Claude-2, etc.

    private Integer maxTokens; // Límite de tokens
    private Integer maxRequests; // Límite de peticiones
    private Duration timeWindow; // Ventana temporal (ej: 1 hora, 1 día)
}

