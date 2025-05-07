package org.example.init.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RestriccionEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoModelo;
    private int limiteMensualTokens;
    private int limiteDiarioConsultas;

    @ManyToOne
    private Empresa empresa;
}
