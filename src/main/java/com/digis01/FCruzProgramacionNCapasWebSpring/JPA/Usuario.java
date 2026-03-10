package com.digis01.FCruzProgramacionNCapasWebSpring.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;


@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idUsuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidopaterno")
    private String apellidoPaterno;

    @Column(name = "apellidosmaterno")
    private String apellidosMaterno;

    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @Column(name = "username")
    private String userName;

    @Column(name = "sexo", length = 2)
    private String sexo;

    @Column(name = "password")
    private String password;

    @Column(name = "curp")
    private String curp;

    @Lob
    @Column(name = "foto")
    private String foto;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idrol")
    private Rol rol;

    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Direccion> direccion = new ArrayList<>();

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidosMaterno, String email, Date fechaNacimiento, String telefono, String celular, String userName, String sexo, String password, String CURP, String foto, int status, com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Rol rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidosMaterno = apellidosMaterno;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.celular = celular;
        this.userName = userName;
        this.sexo = sexo;
        this.password = password;
        this.curp = CURP;
        this.foto = foto;
        this.status = status;
        this.rol = rol;
    }

    

    
    public int getIdUsuario(){
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getApellidoPaterno(){
        return apellidoPaterno;
    }
    
    public void setApellidoPaterno(String apellidoPaterno){
        this.apellidoPaterno = apellidoPaterno;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getApellidosMaterno(){
        return apellidosMaterno;
    }
    
    public void setApellidosMaterno (String apellidosMaterno){
        this.apellidosMaterno = apellidosMaterno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getTelefono () {
        return telefono;
    }
    
    public void setTelefono (String telefono){
        this.telefono = telefono;
    }
    
    public String getCelular (){
        return celular;
    }
    
    public void setCelular (String celular){
        this.celular = celular;
    }
    
    public String getSexo (){
        return sexo;
    }
    
    public void setSexo (String sexo){
        this.sexo = sexo;
    }
    
    public String getPassword (){
        return password;
    }
    
    public void setPassword (String password){
        this.password = password;
    }
    
    public String getCURP (){
        return curp;
    }
    
    public void setCURP (String CURP){
        this.curp = CURP;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Direccion> getDireccion() {
        return direccion;
    }

    public void setDireccion(List<Direccion> direccion) {
        this.direccion = direccion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
}
