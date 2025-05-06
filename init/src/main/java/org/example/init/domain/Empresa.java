package org.example.init.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Empresa {

    // Getters
    @Getter
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
    private Usuario administrador;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<RestriccionEmpresa> restricciones;
    // Getters, setters, constructor

    public String getAdmin() {
        return admin;
    }

    public LocalDate getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public boolean isStatus() {
        return status;
    }

    public String getConsumo() {
        return consumo;
    }

    public Sparky getSparky() {
        return sparky;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<RestriccionEmpresa> getRestricciones() {
        return restricciones;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public void setFechaAfiliacion(LocalDate fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }

    public void setSparky(Sparky sparky) {
        this.sparky = sparky;
    }

    public void setAdministrador(Usuario administrador) {
        this.administrador = administrador;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setRestricciones(List<RestriccionEmpresa> restricciones) {
        this.restricciones = restricciones;
    }

}

