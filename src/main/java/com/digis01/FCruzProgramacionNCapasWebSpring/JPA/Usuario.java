package com.digis01.FCruzProgramacionNCapasWebSpring.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
    name = "Usuario",
    description = "Entidad que representa a un usuario dentro del sistema. Contiene información personal, datos de contacto, credenciales de acceso, rol asignado y direcciones asociadas."
)
@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Usuario {
    
    @Schema(description = "Identificador único del usuario", example = "15")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idUsuario;
    
    @Schema(description = "Identificador único del usuario", example = "15")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",message = "Solo acepto letras")
    @NotNull(message = "No puedo ser nulo")
    @NotEmpty(message = "No puedo ser vacio")
    @Size(min = 3, max = 50, message = "más de 2 letras min")
    @Column(name = "nombre")
    private String nombre;
    
    @Schema(description = "Apellido paterno del usuario", example = "Hernández")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$",message = "Solo acepto letras")
    @Column(name = "apellidopaterno")
    private String apellidoPaterno;
    
    @Schema(description = "Apellido materno del usuario", example = "Ramírez")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",message = "Solo acepto letras")
    @Column(name = "apellidosmaterno")
    private String apellidosMaterno;
    
    @Schema(description = "Correo electrónico del usuario", example = "usuario@email.com")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "No se reconoce como un correo electronico")
    @Column(name = "email")
    private String email;
    
    @Schema(description = "Fecha de nacimiento del usuario", example = "1998-05-12")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha no puede ser nula")
    @Past(message = "Es una fecha futura")
    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;
    
    @Schema(description = "Número telefónico fijo del usuario", example = "7221234567")
    @Pattern(regexp = "^[0-9]{10}$",message = "Solo números y no mas de 10 digitos")
    @Column(name = "telefono")
    private String telefono;
    
    @Schema(description = "Número de teléfono móvil", example = "7229876543")
    @Pattern(regexp = "^[0-9]{10}$",message = "Solo números y no mas de 10 digitos")
    @Column(name = "celular")
    private String celular;
    
    @Schema(description = "Nombre de usuario utilizado para autenticación", example = "carlos.h")
    @Column(name = "username")
    private String userName;
    
    @Schema(description = "Sexo del usuario (M = Masculino, F = Femenino)", example = "M")
    @Column(name = "sexo", length = 2)
    private String sexo;
    
    @Schema(description = "Contraseña del usuario", accessMode = Schema.AccessMode.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,16}$",message = "No se acpeta como contraseña: solo 16 carcateres")
    @Column(name = "password")
    private String password;
    
    @Schema(description = "Clave Única de Registro de Población", example = "HEGC980512HDFRRN09")
    @Pattern(regexp = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9A-Z]{2}$",message = "CURP Invalido")
    @Column(name = "curp")
    private String curp;
    
    @Schema(description = "Imagen del usuario codificada en Base64")
    @Lob
    @Column(name = "foto")
    private String foto;
    
    @Schema(description = "Estado del usuario (1 = Activo, 0 = Inactivo)", example = "1")
    @Column(name = "status")
    private Integer status;
    
    @Schema(description = "Rol asignado al usuario dentro del sistema")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idrol")
    private Rol rol;
    
    @Schema(description = "Lista de direcciones asociadas al usuario")
    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("usuario")
    private List<Direccion> direccion = new ArrayList<>();

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidosMaterno, String email, Date fechaNacimiento, String telefono, String celular, String userName, String sexo, String password, String CURP, String foto, Integer status, com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Rol rol) {
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
