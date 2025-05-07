package org.example.init.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String admin;
    private String ruc;
    private LocalDate fechaAfiliacion;
    private boolean status;
    private String consumo;

    @ElementCollection
    @CollectionTable(name = "empresa_consumo", joinColumns = @JoinColumn(name = "empresa_id"))
    @Column(name = "consumo")
    private List<String> consumos;


    @ManyToOne
    @JoinColumn(name = "sparky_id")
    private Sparky sparky;

    @OneToOne
    @JoinColumn(name = "administrador_id")
    private UserAccount administrador;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<UserAccount> usuarios;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<RestriccionEmpresa> restricciones;

}

