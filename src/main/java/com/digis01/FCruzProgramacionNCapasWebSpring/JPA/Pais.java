package com.digis01.FCruzProgramacionNCapasWebSpring.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Schema(
    name = "Pais",
    description = "Entidad que representa un país dentro del sistema. Un país puede contener múltiples estados asociados."
)
@Entity
@Table(name = "Pais")
public class Pais {
    
    @Schema(
        description = "Identificador único del país",
        example = "1"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpais")
    private Integer idPais;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Estado> estados = new ArrayList<>();

    public Pais() {
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }
}