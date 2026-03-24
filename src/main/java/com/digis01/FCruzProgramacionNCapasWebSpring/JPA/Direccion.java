package com.digis01.FCruzProgramacionNCapasWebSpring.JPA;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(
    name = "Direccion",
    description = "Entidad que representa la dirección asociada a un usuario. Incluye información de ubicación como calle, número interior, número exterior y la colonia a la que pertenece."
)
@Entity
@Table(name = "Direccion")
public class Direccion {
    
    @Schema(
        description = "Identificador único de la dirección",
        example = "10"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int idDireccion;
    
    @Schema(
        description = "Nombre de la calle donde se encuentra la dirección",
        example = "Av. Benito Juárez"
    )
    @Column(name = "calle")
    private String calle;
    
    @Schema(
        description = "Número interior del domicilio (si aplica)",
        example = "12B"
    )
    @Column(name = "numeroiinteriori")
    @JsonProperty("NumeroIInteriori") 
    private String NumeroIInteriori;
    
    @Schema(
        description = "Número exterior del domicilio",
        example = "45"
    )
    @Column(name = "numeroexterior")
    private String numeroExterior;
    
    @Schema(
        description = "Usuario al que pertenece la dirección"
    )
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idusuario")
    @JsonIgnoreProperties("direcciones")
    private Usuario usuario;
    
    @Schema(
        description = "Colonia en la que se encuentra la dirección"
    )
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcolonia")
    private Colonia colonia;

    public Direccion() {
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }
    
    @JsonProperty("NumeroIInteriori")
    public String getNumeroIInteriori() {
        return NumeroIInteriori;
    }

    public void setNumeroIInteriori(String NumeroIInteriori) {
        this.NumeroIInteriori = NumeroIInteriori;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }
}