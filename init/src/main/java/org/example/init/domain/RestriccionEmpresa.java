package org.example.init.domain;

import jakarta.persistence.*;

@Entity
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
