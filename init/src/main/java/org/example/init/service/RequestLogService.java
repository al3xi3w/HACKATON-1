package org.example.init.service;

import org.example.init.domain.RequestLog;
import org.example.init.DTO.RequestLogDTO;
import org.example.init.repository.RequestLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    public RequestLogService(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    // Método para registrar solicitudes
    public void logRequest(String username, String model, String prompt, String response, int tokens) {
        RequestLog log = new RequestLog();
        log.setUsername(username);
        log.setCompany(getUserCompany(username));
        log.setModel(model);
        log.setPrompt(prompt);
        log.setResponse(response);
        log.setTokens(tokens);
        log.setTimestamp(LocalDateTime.now());
        requestLogRepository.save(log);
    }

    // Obtener historial por usuario
    public List<RequestLogDTO> getUserHistory(String username) {
        return requestLogRepository.findByUsernameOrderByTimestampDesc(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convertir entidad a DTO
    private RequestLogDTO convertToDTO(RequestLog log) {
        RequestLogDTO dto = new RequestLogDTO();
        dto.setModel(log.getModel());
        dto.setPrompt(log.getPrompt());
        dto.setResponse(log.getResponse());
        dto.setTokens(log.getTokens());
        dto.setTimestamp(log.getTimestamp());
        return dto;
    }

    // Método simulado para obtener la empresa
    private String getUserCompany(String username) {
        // Puedes reemplazar esto con lógica real en el futuro
        return "sparky";
    }
}