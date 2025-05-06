package org.example.init.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Sparky {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sparky", cascade = CascadeType.ALL)
    private List<Empresa> empresas;

    // Getters, setters, constructor
}
