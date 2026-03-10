package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.ColoniaDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.EstadoDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.MunicipioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.PaisDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.RolDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Colonia;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Estado;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Municipio;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Pais;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Rol;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

@RestController
@RequestMapping("Api")
public class RestControllerUser {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    @Autowired
    private PaisDAOJPAImplementation paisDAOJPAImplementation;
    @Autowired
    private RolDAOJPAImplementation rolDAOJPAImplementation;
    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;
    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;
    @Autowired
    private ColoniaDAOJPAImplementation coloniaDAOJPAImplementation;
    
    @GetMapping("Usuario/{status}")
    public ResponseEntity ObtenerUsuarios(@PathVariable("status") int status) {

        Result result = usuarioDAOJPAImplementation.GetAll();

        if (result.correct) {
            return ResponseEntity.status(status).body(result.objects);
        } else {
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @GetMapping("Usuario/GetById/{idUsuario}")
    public ResponseEntity ObtenerUsuarioById(@PathVariable int idUsuario) {

        Result result = usuarioDAOJPAImplementation.GetById(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result.object);
        } else {
            return ResponseEntity.status(404).body(result.errorMessage);
        }
    }
    
    @PostMapping("Usuario/GetByFilter")
    public ResponseEntity<?> GetByFilter(@RequestBody Usuario usuario){

        Result result = usuarioDAOJPAImplementation.GetByFilter(usuario);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    
    // Para cragar los selects de direccion
    
    @GetMapping("Pais")
    public ResponseEntity ObtenerPaises(){

        Result result = paisDAOJPAImplementation.GetAll();

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @GetMapping("Rol")
    public ResponseEntity ObtenerRol(){

        Result result = rolDAOJPAImplementation.GetAll();

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @GetMapping("Estado/Pais/{idPais}")
    public ResponseEntity<?> getEstadosByPais(@PathVariable int idPais){

        Result result = estadoDAOJPAImplementation.getEstadosByPais(idPais);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @GetMapping("Municipio/Estado/{idEstado}")
    public ResponseEntity<?> getMunicipioByEstado(@PathVariable int idEstado){

        Result result = municipioDAOJPAImplementation.getMunicipioByEstado(idEstado);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @GetMapping("Colonia/Municipio/{idMunicipio}")
    public ResponseEntity<?> getColoniaByMunicipios(@PathVariable int idMunicipio){

        Result result = coloniaDAOJPAImplementation.getColoniaByMunicipios(idMunicipio);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
   
}
