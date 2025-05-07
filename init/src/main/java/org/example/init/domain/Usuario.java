package org.example.init.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
/*
@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    @ElementCollection
    @CollectionTable(name = "usuario_limites", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "limite")
    private List<String> limites;


    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Solicitud> historialSolicitudes;

}

 */
