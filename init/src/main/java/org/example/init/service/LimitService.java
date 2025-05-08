package org.example.init.service;

import org.example.init.repository.RequestLogRepository;
import org.example.init.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class LimitService {

    private final RequestLogRepository requestLogRepository;
    private final UsuarioRepository usuarioRepository;

    public LimitService(RequestLogRepository requestLogRepository,
                        UsuarioRepository usuarioRepository) {
        this.requestLogRepository = requestLogRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public boolean checkUserLimits(String username, String model) {
        // 1. Obtener el usuario (opcional, por si necesitas validar que existe)
        // usuarioRepository.findByUsername(username)
        //     .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Límites por defecto
        int maxTokensPerDay = 1000;
        int maxRequestsPerHour = 100;

        // 3. Verificar tokens usados en las últimas 24h
        LocalDateTime dayAgo = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        Integer tokensUsed = requestLogRepository.sumTokensByUserAndModelAndTimeRange(
                username, model, dayAgo, LocalDateTime.now());

        if (tokensUsed != null && tokensUsed >= maxTokensPerDay) {
            return false;
        }

        // 4. Verificar cantidad de solicitudes en la última hora
        LocalDateTime hourAgo = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        Integer requestsCount = requestLogRepository.countByUserAndModelAndTimeRange(
                username, model, hourAgo, LocalDateTime.now());

        return requestsCount == null || requestsCount < maxRequestsPerHour;
    }

    // Método alternativo simple (por compatibilidad con versiones anteriores)
    public boolean verificarLimite(String username, int tokensSolicitados) {
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        Integer tokensUsadosHoy = requestLogRepository.sumTokensByUserSince(username, today);
        return (tokensUsadosHoy == null ? 0 : tokensUsadosHoy) + tokensSolicitados <= 500;
    }
}