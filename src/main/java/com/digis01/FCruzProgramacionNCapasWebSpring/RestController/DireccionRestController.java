package com.digis01.FCruzProgramacionNCapasWebSpring.RestController;

import com.digis01.FCruzProgramacionNCapasWebSpring.DAO.DireccionDAOJPAImplementation;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Direccion;
import com.digis01.FCruzProgramacionNCapasWebSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Api/Direccion")
public class DireccionRestController {
    
    @Autowired
    private DireccionDAOJPAImplementation direccionDAOJPAImplementation;
    
    @PutMapping
    public Result ActualizarDireccion(@RequestBody Direccion direccion){
        return direccionDAOJPAImplementation.Update(direccion);
    }
    
    @DeleteMapping("/{idDireccion}")
    public Result Eliminar(@PathVariable int idDireccion) {
        return direccionDAOJPAImplementation.DeleteDireccion(idDireccion);
    }
    
    @PostMapping("/Add")
    public Result Add(@RequestBody Direccion direccion) {
        return direccionDAOJPAImplementation.Add(direccion);
    }
    

}
