package org.example.init.controller;


import jakarta.validation.constraints.NotBlank;
import org.example.init.DTO.RequestLogDTO;
import org.example.init.service.RequestLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ai/history")
public class HistoryController {

    private final RequestLogService requestLogService;

    public HistoryController(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RequestLogDTO>> getUserHistory(
            @PathVariable @NotBlank String username) {

        // Usa el método correcto getUserHistory (no obtenerHistorial)
        List<RequestLogDTO> historial = requestLogService.getUserHistory(username);

        if (historial == null || historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(historial);
    }
}