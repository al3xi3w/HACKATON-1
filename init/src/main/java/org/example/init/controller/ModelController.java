package org.example.init.controller;

import org.example.init.DTO.RequestLogDTO;
import org.example.init.service.LimitService;
import org.example.init.service.RequestLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class ModelController {

    private final ModelService modelService;
    private final LimitService limitService;
    private final RequestLogService requestLogService;

    public ModelController(ModelService modelService,
                           LimitService limitService,
                           RequestLogService requestLogService) {
        this.modelService = modelService;
        this.limitService = limitService;
        this.requestLogService = requestLogService;
    }

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody ChatRequest request,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        // 1. Verificar límites
        if (!limitService.checkUserLimits(userDetails.getUsername(), request.getModel())) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Has excedido tus límites de uso");
        }

        // 2. Procesar solicitud
        ChatResponse response = modelService.processChat(request);

        // 3. Registrar solicitud
        requestLogService.logRequest(
                userDetails.getUsername(),
                request.getModel(),
                request.getPrompt(),
                response.getText(),
                response.getTokensUsed()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<RequestLogDTO>> getHistory(@AuthenticationPrincipal UserDetails userDetails) {
        List<RequestLogDTO> history = requestLogService.getUserHistory(userDetails.getUsername());
        return ResponseEntity.ok(history);
    }
}