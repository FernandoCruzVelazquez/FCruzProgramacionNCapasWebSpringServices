package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.UsuarioDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {
    
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;
    
    @GetMapping
    public String Holamundo(){
        return "HolaMundo";
    }
   
    @GetMapping("saludo/{nombre}")
    public String Saludo(@PathVariable("nombre") String nombre){
        return "Hola " + nombre;
    }
    
    @GetMapping("saludo")
    public String Saludo2(@RequestParam("nombre") String nombre){
        return "Hola " + nombre;
    }
    
    //suma de dos nuemros por medio del GET
    
    @GetMapping("suma/{n1}/{n2}")
    public String suma(@PathVariable int n1, @PathVariable int n2) {

        int suma = n1 + n2;
        return "Suma: " + n1 + " + " + n2 + " = " + suma;
    }
    
    
    // suma de n elementos - POST / mandar un arreglo
    
    @PostMapping("/suma")
    public String suma(@RequestBody List<Integer> numeros) {

        int suma = 0;

        for (int num : numeros) {
            suma += num;
        }

        return "La suma total es: " + suma;
    }
    
    // retornar la lista de usuarios - GET
    
    @GetMapping("Usuario/{status}")
    public ResponseEntity ObtenerUsuarios(@PathVariable("status") int status) {

        Result result = usuarioDAOJPAImplementation.GetAll();

        if (result.correct) {
            return ResponseEntity.status(status).body(result.objects);
        } else {
            return ResponseEntity.status(500).body(result.errorMessage);
        }
    }
    

}
