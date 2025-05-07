package org.example.init.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private String text;         // Respuesta del modelo
    private int tokensUsed;      // Tokens estimados o reales
}
