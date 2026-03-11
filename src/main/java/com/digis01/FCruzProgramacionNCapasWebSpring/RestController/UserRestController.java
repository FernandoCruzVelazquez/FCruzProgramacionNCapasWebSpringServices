package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;

import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Usuario;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("Api/Usuario")
public class UserRestController {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @GetMapping("/{status}")
    public ResponseEntity ObtenerUsuarios(@PathVariable("status") int status) {

        Result result = usuarioDAOJPAImplementation.GetAll();

        if (result.correct) {
            return ResponseEntity.status(status).body(result.objects);
        } else {
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Result> Add(@RequestPart("datos") Usuario usuario, 
                                      @RequestPart(name = "imagen", required = false) MultipartFile imagen) {
        Result result = new Result();
        try {
            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);
                usuario.setFoto("data:" + imagen.getContentType() + ";base64," + base64);
            }

            result = usuarioDAOJPAImplementation.Add(usuario);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = "Error en el servidor: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
    
//    @GetMapping
//    public ResponseEntity GetAll() {
//
//        try {
//            Result result = usuarioDAOJPAImplementation.GetAll();
//
//            if (result.correct) {
//                if (result.objects != null || !result.objects.isEmpty()) {
//                    return ResponseEntity.ok(result);
//                } else {
//                    return ResponseEntity.noContent().build();
//                }
//            } else {
//                return ResponseEntity.badRequest().body(result.errorMessage);
//            }
//        } catch (Exception ex) {
//            return ResponseEntity.status(500).body(ex);
//        }
//
//    }
    

    
    @GetMapping("/GetById/{idUsuario}")
    public ResponseEntity ObtenerUsuarioById(@PathVariable int idUsuario) {

        Result result = usuarioDAOJPAImplementation.GetById(idUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result.object);
        } else {
            return ResponseEntity.status(404).body(result.errorMessage);
        }
    }
    
    @PostMapping("/GetByFilter")
    public ResponseEntity<?> GetByFilter(@RequestBody Usuario usuario){

        Result result = usuarioDAOJPAImplementation.GetByFilter(usuario);

        if(result.correct){
            return ResponseEntity.ok(result.objects);
        }else{
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    
    @GetMapping("/Detalle/{id}")
    public Result detalleUsuario(@PathVariable int id){

        Result result = usuarioDAOJPAImplementation.GetById(id);

        return result;
    }
    
    @PutMapping("/Update")
    public Result Update(@RequestBody Usuario usuario){
        return usuarioDAOJPAImplementation.Update(usuario);
    }
    
    @PostMapping("/Usuario/Foto/{id}")
    public Result actualizarFoto(@PathVariable int id,@RequestParam("imagen") MultipartFile foto){

        try{

            String tipo = foto.getContentType();

            if("image/jpeg".equals(tipo) || "image/png".equals(tipo)){

                byte[] bytes = foto.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);
                String imagenFinal = "data:" + tipo + ";base64," + base64;

                return usuarioDAOJPAImplementation.UpdateFoto(id, imagenFinal);
            }

        }catch(Exception e){
            Result result = new Result();
            result.correct = false;
            return result;
        }

        Result result = new Result();
        result.correct = false;
        return result;
    }
    
    
    
}
