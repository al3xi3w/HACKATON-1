package org.example.init.repository;

import org.example.init.domain.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

    // Método existente
    List<RequestLog> findByUsernameOrderByTimestampDesc(String username);

    // Nuevos métodos para límites
    @Query("SELECT SUM(r.tokens) FROM RequestLog r WHERE r.username = :username AND r.timestamp >= :since")
    Integer sumTokensByUserSince(@Param("username") String username, @Param("since") LocalDateTime since);

    @Query("SELECT SUM(r.tokens) FROM RequestLog r WHERE r.company = :company AND r.model = :model AND r.timestamp BETWEEN :start AND :end")
    Integer sumTokensByCompanyAndModelAndTimeRange(
            @Param("company") String company,
            @Param("model") String model,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(r) FROM RequestLog r WHERE r.company = :company AND r.model = :model AND r.timestamp BETWEEN :start AND :end")
    Integer countByCompanyAndModelAndTimeRange(
            @Param("company") String company,
            @Param("model") String model,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT SUM(r.tokens) FROM RequestLog r WHERE r.username = :username AND r.model = :model AND r.timestamp BETWEEN :start AND :end")
    Integer sumTokensByUserAndModelAndTimeRange(
            @Param("username") String username,
            @Param("model") String model,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(r) FROM RequestLog r WHERE r.username = :username AND r.model = :model AND r.timestamp BETWEEN :start AND :end")
    Integer countByUserAndModelAndTimeRange(
            @Param("username") String username,
            @Param("model") String model,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}